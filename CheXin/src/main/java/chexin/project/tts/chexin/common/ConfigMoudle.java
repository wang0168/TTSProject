package chexin.project.tts.chexin.common;

import java.util.ArrayList;
import java.util.List;

import chexin.project.tts.chexin.bean.CarDetailBean;
import chexin.project.tts.chexin.bean.ConfigBean;
import chexin.project.tts.chexin.bean.EffectiveBean;


/**
 * Created by sjb on 2016/1/21.
 */
public class ConfigMoudle {

    private ArrayList<ConfigBean> provinceBeans;
    private List<ConfigBean> carTypeBeans;
    private List<ConfigBean> goodsUnitBeans;
    private List<ConfigBean> goodsTypeBeans;
    private List<ConfigBean> carLongBeans;
    private List<CarDetailBean> carDetailBeans;
    private List<EffectiveBean> carEffectiveBeans;
    private List<EffectiveBean> goodEffectiveBeans;

    public List<EffectiveBean> getGoodEffectiveBeans() {
        if (goodEffectiveBeans == null) {
            goodEffectiveBeans = new ArrayList<>();
        }
        return goodEffectiveBeans;
    }

    public void setGoodEffectiveBeans(List<EffectiveBean> goodEffectiveBeans) {
        this.goodEffectiveBeans = goodEffectiveBeans;
    }

    public List<EffectiveBean> getCarEffectiveBeans() {
        if (carEffectiveBeans == null) {
            carEffectiveBeans = new ArrayList<>();
        }
        return carEffectiveBeans;
    }

    public void setCarEffectiveBeans(List<EffectiveBean> carEffectiveBeans) {
        this.carEffectiveBeans = carEffectiveBeans;
    }

    public List<CarDetailBean> getCarDetailBeans() {
        if (carDetailBeans == null) {
            carDetailBeans = new ArrayList<>();
        }
        return carDetailBeans;
    }

    public void setCarDetailBeans(List<CarDetailBean> carDetailBeans) {
        this.carDetailBeans = carDetailBeans;
    }

    public List<ConfigBean> getCarLongBeans() {
        if (carLongBeans == null) {
            carLongBeans = new ArrayList<>();
        }
        return carLongBeans;
    }

    public void setCarLongBeans(List<ConfigBean> carLongBeans) {
        this.carLongBeans = carLongBeans;
    }

    public List<ConfigBean> getCarTypeBeans() {
        if (carTypeBeans == null) {
            carTypeBeans = new ArrayList<>();
        }
        return carTypeBeans;
    }

    public void setCarTypeBeans(List<ConfigBean> carTypeBeans) {
        this.carTypeBeans = carTypeBeans;
    }

    public List<ConfigBean> getGoodsTypeBeans() {
        if (goodsTypeBeans == null) {
            goodsTypeBeans = new ArrayList<>();
        }
        return goodsTypeBeans;
    }

    public void setGoodsTypeBeans(List<ConfigBean> goodsTypeBeans) {
        this.goodsTypeBeans = goodsTypeBeans;
    }

    public List<ConfigBean> getGoodsUnitBeans() {
        if (goodsUnitBeans == null) {
            goodsUnitBeans = new ArrayList<>();
        }
        return goodsUnitBeans;

    }

    public void setGoodsUnitBeans(List<ConfigBean> goodsUnitBeans) {
        this.goodsUnitBeans = goodsUnitBeans;
    }


    public ArrayList<ConfigBean> getProvinceBeans() {
        if (provinceBeans == null) {
            provinceBeans = new ArrayList<>();
        }
        return provinceBeans;
    }

    public void setProvinceBeans(ArrayList<ConfigBean> provinceBeans) {
        this.provinceBeans = provinceBeans;
    }

    private static class Moudle {
        protected final static ConfigMoudle mInstance = new ConfigMoudle();
    }

    public static ConfigMoudle getInstance() {
        return Moudle.mInstance;
    }


}
