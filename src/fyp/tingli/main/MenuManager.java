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
 * �˵���������
 * 
 * @author liufeng
 * @date 2013-08-08
 */
public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);

	public static void main(String[] args) {
		// �������û�Ψһƾ֤
		String appId = "wx45a046429b11abeb";
		// �������û�Ψһƾ֤��Կ
		String appSecret = "c407cf911be90d4857ca8b489e1f0754";

		// ���ýӿڻ�ȡaccess_token
		AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);

		if (null != at) {
			// ���ýӿڴ����˵�
			int result = WeixinUtil.createMenu(getMenu(), at.getToken());

			// �жϲ˵��������
			if (0 == result)
				log.info("�˵������ɹ���");
			else
				log.info("�˵�����ʧ�ܣ������룺" + result);
		}
	}

	/**
	 * ��װ�˵�����
	 * 
	 * @return
	 */
	private static Menu getMenu() {
		CommonButton btn11 = new CommonButton();
		btn11.setName("�𳵲�ѯ");
		btn11.setType("click");
		btn11.setKey("11");

		CommonButton btn12 = new CommonButton();
		btn12.setName("�����ѯ");
		btn12.setType("click");
		btn12.setKey("12");

		CommonButton btn13 = new CommonButton();
		btn13.setName("��;����");
		btn13.setType("click");
		btn13.setKey("13");

		ViewButton btn14 = new ViewButton();
		btn14.setName("������ѯ");
		btn14.setType("view");
		btn14.setUrl("http://map.baidu.com/mobile/webapp/third/transit/force=superman?third_party=hao123");
		
		ViewButton btn15 = new ViewButton();
		btn15.setName("��Ʊ��ѯ");
		btn15.setType("view");
		btn15.setUrl("http://mobile.12306.cn/weixin/wxcore/init");

		CommonButton btn21 = new CommonButton();
		btn21.setName("����Ԥ��");
		btn21.setType("click");
		btn21.setKey("21");

		CommonButton btn22 = new CommonButton();
		btn22.setName("�ܱ�����");
		btn22.setType("click");
		btn22.setKey("22");

		CommonButton btn23 = new CommonButton();
		btn23.setName("�ܱ��Ź�");
		btn23.setType("click");
		btn23.setKey("23");

		CommonButton btn24 = new CommonButton();
		btn24.setName("ͣ��λԤ��");
		btn24.setType("click");
		btn24.setKey("24");
		
		CommonButton btn25 = new CommonButton();
		btn25.setName("���Ż�չ����չ��");
		btn25.setType("click");
		btn25.setKey("25");

		CommonButton btn31 = new CommonButton();
		btn31.setName("�Ϻ�·����ѯ");
		btn31.setType("click");
		btn31.setKey("31");
	
		CommonButton btn32 = new CommonButton();
		btn32.setName("���⳵��Ŀ��");
		btn32.setType("click");
		btn32.setKey("32");
		
		
		CommonButton btn33 = new CommonButton();
		btn33.setName("�Ϻ����ι���");
		btn33.setType("click");
		btn33.setKey("34");
		
		ViewButton btn34 = new ViewButton();
		btn34.setName("��ѯ����");
		btn34.setType("view");
		btn34.setUrl("http://liufeng.gotoip2.com/xiaoqrobot/help.jsp");
        //btn33.setUrl("http://192.168.1.105/web.FYP/index.jsp");//
		
		
		
		
        ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("��ͨ��ѯ");
		mainBtn1.setSub_button(new Button[] {btn11, btn12, btn13, btn14, btn15 });

		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("���з���");
		mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23, btn24,btn25});

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("����");
		mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33, btn34 });

		/**
		 * ���ǹ��ں�xiaoqrobotĿǰ�Ĳ˵��ṹ��ÿ��һ���˵����ж����˵���<br>
		 * 
		 * ��ĳ��һ���˵���û�ж����˵��������menu����ζ����أ�<br>
		 * ���磬������һ���˵���ǡ��������顱����ֱ���ǡ���ĬЦ��������ômenuӦ���������壺<br>
		 * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
		 */
		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

		return menu;
	}
}