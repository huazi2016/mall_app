package com.mall.demo.utils;

import android.widget.ImageView;

import com.mall.demo.R;
import com.mall.demo.bean.MallBo;

import java.util.ArrayList;
import java.util.List;

/**
 * time   : 2021/5/21
 * desc   :
 */
public class InfoUtil {

    public static List<MallBo> getGoodsList(String username) {
        List<MallBo> goodLists = new ArrayList<>();
        String content = "批准文号\n" +
                "因为批准文号是药品生产合法的标志。如国药准字H******号，“H”是代表化学药品，“Z”是代表中药，“B”是保健品，“s”是生物制品，“J”是进口药品等，没有批准文号的是伪劣药品，千万别买。\n" +
                "主要成分\n" +
                "说明药品是由什么原材料构成的，以及原材料的化学名称和化学式是什么，以及注意事项。\n" +
                "用法用量\n" +
                "“用法”是根据该药的剂型与特性，注明口服、注射、饭前或饭后、外用及每日用药次数等，“用量”一般指体型正常成人的用药剂量。包括每次用药剂量及每日最大用量。其中1g {克}=1000mg {毫克}，如0.25g =250mg。儿童是按公斤体重计算，老年人因为吸收，排泄等生理功能，有所降低，所以最好用成人四分之三的量。\n" +
                "禁忌和慎用\n" +
                "“禁忌”是绝对不能用的，如对青霉素过敏的病人，是绝对不能用青霉素的，否则会危及生命。“慎用”是可以用，但必须慎重考虑，权衡其利弊，在利大于弊的情况下方可使用，并须密切观察是否有不良反应，以便及时采取措施，最好是在医师指导下用药。";
        MallBo bo01 = new MallBo();
        bo01.img = "1";
        bo01.name = "康恩贝肠炎宁片";
        bo01.subtitle = "拉肚子用肠炎宁, 一身轻松";
        bo01.price = "25.2";
        bo01.order = 0;
        bo01.username = username;
        bo01.content = content;
        goodLists.add(bo01);

        MallBo bo02 = new MallBo();
        bo02.img = "2";
        bo02.name = "康恩贝肠炎宁片";
        bo02.subtitle = "拉肚子用肠炎宁, 一身轻松";
        bo02.price = "25.2";
        bo02.order = 0;
        bo02.username = username;
        bo02.content = content;
        goodLists.add(bo02);

        MallBo bo03 = new MallBo();
        bo03.img = "3";
        bo03.name = "康恩贝肠炎宁片";
        bo03.subtitle = "拉肚子用肠炎宁, 一身轻松";
        bo03.price = "25.2";
        bo03.order = 0;
        bo03.username = username;
        bo03.content = content;
        goodLists.add(bo03);

        MallBo bo04 = new MallBo();
        bo04.img = "4";
        bo04.name = "康恩贝肠炎宁片";
        bo04.subtitle = "拉肚子用肠炎宁, 一身轻松";
        bo04.price = "25.2";
        bo04.order = 0;
        bo04.username = username;
        bo04.content = content;
        goodLists.add(bo04);

        MallBo bo05 = new MallBo();
        bo05.img = "5";
        bo05.name = "康恩贝肠炎宁片";
        bo05.subtitle = "拉肚子用肠炎宁, 一身轻松";
        bo05.price = "25.2";
        bo05.order = 0;
        bo05.username = username;
        bo05.content = content;
        goodLists.add(bo05);

        MallBo bo06 = new MallBo();
        bo06.img = "6";
        bo06.name = "康恩贝肠炎宁片";
        bo06.subtitle = "拉肚子用肠炎宁, 一身轻松";
        bo06.price = "25.2";
        bo06.order = 0;
        bo06.username = username;
        bo06.content = content;
        goodLists.add(bo06);

        MallBo bo07 = new MallBo();
        bo07.img = "7";
        bo07.name = "康恩贝肠炎宁片";
        bo07.subtitle = "拉肚子用肠炎宁, 一身轻松";
        bo07.price = "25.2";
        bo07.order = 0;
        bo07.username = username;
        bo07.content = content;
        goodLists.add(bo07);

        MallBo bo08 = new MallBo();
        bo08.img = "8";
        bo08.name = "康恩贝肠炎宁片";
        bo08.subtitle = "拉肚子用肠炎宁, 一身轻松";
        bo08.price = "25.2";
        bo08.order = 0;
        bo08.username = username;
        bo08.content = content;
        goodLists.add(bo08);

        MallBo bo09 = new MallBo();
        bo09.img = "9";
        bo09.name = "康恩贝肠炎宁片";
        bo09.subtitle = "拉肚子用肠炎宁, 一身轻松";
        bo09.price = "25.2";
        bo09.order = 0;
        bo09.username = username;
        bo09.content = content;
        goodLists.add(bo09);

        MallBo bo10 = new MallBo();
        bo10.img = "10";
        bo10.name = "康恩贝肠炎宁片";
        bo10.subtitle = "拉肚子用肠炎宁, 一身轻松";
        bo10.price = "25.2";
        bo10.order = 0;
        bo10.username = username;
        bo10.content = content;
        goodLists.add(bo10);

        return goodLists;
    }

    public static void setImg(String imgType, ImageView tvImg) {
        switch (imgType) {
            case "1" : {
                tvImg.setImageResource(R.drawable.img01);
                break;
            }
            case "2" : {
                tvImg.setImageResource(R.drawable.img02);
                break;
            }
            case "3" : {
                tvImg.setImageResource(R.drawable.img03);
                break;
            }
            case "4" : {
                tvImg.setImageResource(R.drawable.img04);
                break;
            }
            case "5" : {
                tvImg.setImageResource(R.drawable.img05);
                break;
            }
            case "6" : {
                tvImg.setImageResource(R.drawable.img06);
                break;
            }
            case "7" : {
                tvImg.setImageResource(R.drawable.img07);
                break;
            }
            case "8" : {
                tvImg.setImageResource(R.drawable.img08);
                break;
            }
            case "9" : {
                tvImg.setImageResource(R.drawable.img09);
                break;
            }
            case "10" : {
                tvImg.setImageResource(R.drawable.img10);
                break;
            }
            default:{
                break;
            }
        }
    }
}
