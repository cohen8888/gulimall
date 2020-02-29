package com.atguigu.gulimall.pms.service.impl;

import com.atguigu.gulimall.commons.to.SkuSaleInfoTO;
import com.atguigu.gulimall.pms.dao.*;
import com.atguigu.gulimall.pms.entity.*;
import com.atguigu.gulimall.pms.feign.SmsSkuSaleInfoServiceFeign;
import com.atguigu.gulimall.pms.vo.BaseAttrVO;
import com.atguigu.gulimall.pms.vo.SaleAttrVO;
import com.atguigu.gulimall.pms.vo.SkuVO;
import com.atguigu.gulimall.pms.vo.SpuAllInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gulimall.commons.bean.PageVo;
import com.atguigu.gulimall.commons.bean.Query;
import com.atguigu.gulimall.commons.bean.QueryCondition;

import com.atguigu.gulimall.pms.service.SpuInfoService;

@Slf4j
@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SpuInfoDescDao spuInfoDescDao;

    @Autowired
    private ProductAttrValueDao productAttrValueDao;

    @Autowired
    private SkuInfoDao skuInfoDao;

    @Autowired
    private SkuImagesDao skuImagesDao;

    @Autowired
    private AttrDao attrDao;

    @Autowired
    private SkuSaleAttrValueDao skuSaleAttrValueDao;

    @Autowired
    private SmsSkuSaleInfoServiceFeign smsSkuSaleInfoServiceFeign;
    @Override
    public PageVo queryPage(QueryCondition params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageVo(page);
    }

    @Override
    public PageVo queryPageByCatId(QueryCondition queryCondition, Long catId) {
        //1.封装查询条件
        QueryWrapper<SpuInfoEntity> objectQueryWrapper = new QueryWrapper<>();
        if (catId != 0){
            //搜索全部
            objectQueryWrapper.eq("catalog_id", catId);
            if(!StringUtils.isEmpty(queryCondition.getKey())){

                objectQueryWrapper.and(obj ->{
                    obj.like("spu_name", queryCondition.getKey());
                    obj.or().like("id", queryCondition.getKey());
                });
            }
        }

        //2.封装翻页条件
        IPage<SpuInfoEntity> page = new Query<SpuInfoEntity>().getPage(queryCondition);

        //3.查询数据库
        IPage<SpuInfoEntity> data = this.page(page, objectQueryWrapper);
        PageVo pageVo = new PageVo(data);
        return pageVo;
    }

    @Override
    public void saveAllInfoVO(SpuAllInfoVO spuAllInfoVO) {
        //1.保存spu的基本信息
        Long spuId = this.saveSpuBaseInfo(spuAllInfoVO);
        //2.保存spu的所有图片信息
        this.saveSpuInfoImages(spuId, spuAllInfoVO.getSpuImages());

        //3.保存spu的基本属性信息
        List<BaseAttrVO> baseAttrs = spuAllInfoVO.getBaseAttrs();
        this.saveSpuBaseInfoAttrs(spuId, baseAttrs);

        //4.保存sku以及sku的营销相关信息
        this.saveSkuInfos(spuId, spuAllInfoVO.getSkus());

        //5.sku的优惠信息

    }

    //保存sku的所有详情
    @Override
    public void saveSkuInfos(Long spuId, List<SkuVO> skus) {
        //查出spu的
        SpuInfoEntity spuInfo = this.getById(spuId);
        //保存sku的info信息
        for(SkuVO skuVO: skus){
            String[] images = skuVO.getImages();
            SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
            skuInfoEntity.setBrandId(spuInfo.getBrandId());
            skuInfoEntity.setCatalogId(spuInfo.getCatalogId());
            skuInfoEntity.setPrice(skuVO.getPrice());
            skuInfoEntity.setSkuCode(UUID.randomUUID().toString().substring(0, 5).toUpperCase());
            if(images != null && images.length > 0){
                skuInfoEntity.setSkuDefaultImg(images[0]);
            }
            skuInfoEntity.setSpuId(spuId);
            skuInfoEntity.setSkuDesc(skuVO.getSkuDesc());
            skuInfoEntity.setSkuName(skuVO.getSkuName());
            skuInfoEntity.setSkuSubtitle(skuVO.getSkuSubtitle());
            skuInfoEntity.setSkuTitle(skuVO.getSkuTitle());
            skuInfoEntity.setWeight(skuVO.getWeight());
            skuInfoDao.insert(skuInfoEntity);

            //2.保存所有sku的所有图片
            Long skuId = skuInfoEntity.getSkuId();
            for (int i = 0; i < images.length ; i++) {
                SkuImagesEntity imagesEntity = new SkuImagesEntity();
                imagesEntity.setSkuId(skuId);
                imagesEntity.setDefaultImg(i == 0 ? 1 : 0);
                imagesEntity.setImgUrl(images[i]);
                imagesEntity.setImgSort(0);
                skuImagesDao.insert(imagesEntity);
            }

            //3.当前sku的所有销售属性组合保存
            List<SaleAttrVO> saleAttrs = skuVO.getSaleAttrs();
            for(SaleAttrVO attrVO: saleAttrs){
                SkuSaleAttrValueEntity entity = new SkuSaleAttrValueEntity();
                entity.setAttrId(attrVO.getAttrId());
                AttrEntity attrEntity = attrDao.selectById(attrVO.getAttrId());
                entity.setAttrName(attrEntity.getAttrName());
                entity.setAttrSort(0);
                entity.setAttrValue(attrVO.getAttrValue());
                entity.setSkuId(skuId);
                skuSaleAttrValueDao.insert(entity);
            }

            //4.把优惠信息，组装到Tanslation Object对象中，发给sms系统处理
            SkuSaleInfoTO skuSaleInfoTO = new SkuSaleInfoTO();
            BeanUtils.copyProperties(skuVO, skuSaleInfoTO);
            skuSaleInfoTO.setSpuId(skuId);
            //调用远程接口
            log.info("pms 发数据给", skuSaleInfoTO);
            smsSkuSaleInfoServiceFeign.saveSkuSaleInfos(skuSaleInfoTO);
            log.info("pms 发数据完毕");

        }
    }

    public void saveSpuBaseInfoAttrs(Long spuId, List<BaseAttrVO> baseAttrs) {

        List<ProductAttrValueEntity> allSave = new ArrayList<>();

        for (BaseAttrVO bvo : baseAttrs){
            ProductAttrValueEntity entity = new ProductAttrValueEntity();
            entity.setAttrId(bvo.getAttrId());
            entity.setAttrName(bvo.getAttrName());
            String[] selected = bvo.getValueSelected();
            entity.setAttrValue(com.atguigu.gulimall.commons.utils.StringUtils.arrayToStringWithSeperator(selected, ","));
            entity.setAttrSort(0);
            entity.setQuickShow(1);
            entity.setSpuId(spuId);
            allSave.add(entity);
        }

        productAttrValueDao.insertBatch(allSave);
    }

    //保存spu的图片描述信息
    public void saveSpuInfoImages(Long spuId, String[] spuImages) {
        if (spuImages != null && spuImages.length > 0){
            StringBuffer urls = new StringBuffer();
            for(int i = 0; i < spuImages.length; i++){
                urls.append(spuImages[i]);
                if (i < spuImages.length - 1)
                    urls.append(",");
            }
            SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
            spuInfoDescEntity.setDecript(urls.toString());
            spuInfoDescDao.insert(spuInfoDescEntity);
        }
    }

    public Long saveSpuBaseInfo(SpuAllInfoVO spuAllInfoVO) {

        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(spuAllInfoVO, spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.save(spuInfoEntity);

        return spuInfoEntity.getId();
    }

}