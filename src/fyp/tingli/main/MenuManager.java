package fyp.tingli.main;


import fyp.tingli.util.WeixinUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fyp.tingli.pojo.AccessToken;
import fyp.tingli.pojo.Button;
import fyp.tingli.pojo.CommonButton;
import fyp.tingli.pojo.ComplexButton;
import fyp.tingli.pojo.Menu;
import fyp.tingli.pojo.ViewButton;


/**
 * 菜单管理器类
 * 
 * @author liufeng
 * @date 2013-08-08
 */
public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);

	public static void main(String[] args) {
		// 第三方用户唯一凭证
		String appId = "wx45a046429b11abeb";
		// 第三方用户唯一凭证密钥
		String appSecret = "c407cf911be90d4857ca8b489e1f0754";

		// 调用接口获取access_token
		AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);

		if (null != at) {
			// 调用接口创建菜单
			int result = WeixinUtil.createMenu(getMenu(), at.getToken());

			// 判断菜单创建结果
			if (0 == result)
				log.info("菜单创建成功！");
			else
				log.info("菜单创建失败，错误码：" + result);
		}
	}

	/**
	 * 组装菜单数据
	 * 
	 * @return
	 */
	private static Menu getMenu() {
		CommonButton btn11 = new CommonButton();
		btn11.setName("火车查询");
		btn11.setType("click");
		btn11.setKey("11");

		CommonButton btn12 = new CommonButton();
		btn12.setName("航班查询");
		btn12.setType("click");
		btn12.setKey("12");

		CommonButton btn13 = new CommonButton();
		btn13.setName("长途汽车");
		btn13.setType("click");
		btn13.setKey("13");

		ViewButton btn14 = new ViewButton();
		btn14.setName("公交查询");
		btn14.setType("view");
		btn14.setUrl("http://map.baidu.com/mobile/webapp/third/transit/force=superman?third_party=hao123");
		
		ViewButton btn15 = new ViewButton();
		btn15.setName("余票查询");
		btn15.setType("view");
		btn15.setUrl("http://mobile.12306.cn/weixin/wxcore/init");

		CommonButton btn21 = new CommonButton();
		btn21.setName("天气预报");
		btn21.setType("click");
		btn21.setKey("21");

		CommonButton btn22 = new CommonButton();
		btn22.setName("周边搜索");
		btn22.setType("click");
		btn22.setKey("22");

		CommonButton btn23 = new CommonButton();
		btn23.setName("周边团购");
		btn23.setType("click");
		btn23.setKey("23");

		CommonButton btn24 = new CommonButton();
		btn24.setName("停车位预定");
		btn24.setType("click");
		btn24.setKey("24");
		
		CommonButton btn25 = new CommonButton();
		btn25.setName("虹桥会展中心展览");
		btn25.setType("click");
		btn25.setKey("25");

		CommonButton btn31 = new CommonButton();
		btn31.setName("上海路况查询");
		btn31.setType("click");
		btn31.setKey("31");
	
		CommonButton btn32 = new CommonButton();
		btn32.setName("出租车价目表");
		btn32.setType("click");
		btn32.setKey("32");
		
		
		CommonButton btn33 = new CommonButton();
		btn33.setName("上海旅游攻略");
		btn33.setType("click");
		btn33.setKey("34");
		
		ViewButton btn34 = new ViewButton();
		btn34.setName("查询帮助");
		btn34.setType("view");
		btn34.setUrl("http://liufeng.gotoip2.com/xiaoqrobot/help.jsp");
        //btn33.setUrl("http://192.168.1.105/web.FYP/index.jsp");//
		
		
		
		
        ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("交通查询");
		mainBtn1.setSub_button(new Button[] {btn11, btn12, btn13, btn14, btn15 });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("休闲服务");
		mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23, btn24,btn25});

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("更多");
		mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33, btn34 });

		/**
		 * 这是公众号xiaoqrobot目前的菜单结构，每个一级菜单都有二级菜单项<br>
		 * 
		 * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？<br>
		 * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：<br>
		 * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
		 */
		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}
}