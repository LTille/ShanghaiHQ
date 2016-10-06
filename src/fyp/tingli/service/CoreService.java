package fyp.tingli.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.Query;
import org.hibernate.Session;
import fyp.tingli.dao.FlightDao;
import fyp.tingli.dao.TrainDao;
import fyp.tingli.functions.ArticleList;
import fyp.tingli.functions.BaiduMapService;
import fyp.tingli.functions.BusSearch;
import fyp.tingli.functions.ChineseToEnglish;
import fyp.tingli.functions.DisplayInfo;
import fyp.tingli.functions.Functions;
import fyp.tingli.functions.Groupon;
import fyp.tingli.functions.SearchAround;
import fyp.tingli.functions.SearchWeather;
import fyp.tingli.message.resp.Article;
import fyp.tingli.message.resp.NewsMessage;
import fyp.tingli.message.resp.TextMessage;
import fyp.tingli.model.WechatUser;
import fyp.tingli.util.Hibernate;
import fyp.tingli.util.MessageUtil;

/**
 * ���ķ�����
 *
 * @author Ting Li
 * @date 2014-10-20
 */
public class CoreService {

	/**
	 * ����΢�ŷ���������
	 *
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;

		try {
			// Ĭ�Ϸ��ص��ı���Ϣ����
			String respContent = "���������쳣�����Ժ����ԣ�";

			// xml��������
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// ���ͷ��ʺţ�open_id��
			String fromUserName = requestMap.get("FromUserName");
			// �����ʺ�
			String toUserName = requestMap.get("ToUserName");
			// ��Ϣ����
			String msgType = requestMap.get("MsgType");


			// �ظ��ı���Ϣ
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);


			// �ı���Ϣ

			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {

				String content = requestMap.get("Content").trim();

				if(content.startsWith("��������")){

					String keyWord = content.replaceAll("^��������", "").trim();

					System.out.println(keyWord);

					if("".equals(keyWord)){

						  respContent = "���������쳣";

					}else{

						//respContent = TrainSearch.searchTrain(keyWord);
						String trainNo = keyWord.substring(0, keyWord.indexOf("~"));
				    String runDate = keyWord.substring(keyWord.indexOf("~")+1);
						TrainDao dao = new TrainDao();
						String train = dao.searchTrain(trainNo,runDate);
						respContent = train;
						System.out.println(train);
					}
					   textMessage.setContent(respContent);
					   respMessage = MessageUtil.textMessageToXml(textMessage);
				   }

				    else if (content.startsWith("��������")){

					  String keyWord = content.replaceAll("^��������", "").trim();
					   if("".equals(keyWord)){

						   respContent = "���������쳣";

					    }else{

						  // respContent = FlightSearch.searchFlight(keyWord);
					    	String flightNo = keyWord.substring(0, keyWord.indexOf("~"));
					        String operationDate = keyWord.substring(keyWord.indexOf("~")+1);
							FlightDao dao = new FlightDao();
							String flight = dao.searchFlight(flightNo,operationDate);
							respContent = flight;
							System.out.println(flight);
					    }
					   textMessage.setContent(respContent);
					   respMessage = MessageUtil.textMessageToXml(textMessage);
				    }
				      else if (content.startsWith("��������")){

						  String keyWord = content.replaceAll("^��������", "").trim();
						   if("".equals(keyWord)){

							   respContent = "���������쳣";

						    }else{

							  respContent = "���Ϻ�������վ������"+keyWord+"������\n" + BusSearch.Bus(ChineseToEnglish.getPingYin(keyWord));
						    }
						   textMessage.setContent(respContent);
						   respMessage = MessageUtil.textMessageToXml(textMessage);
					    }
				      else if (content.startsWith("��Ʊ��ѯ")){

				    	  String keyWord = content.replaceAll("^��Ʊ��ѯ", "").trim();
						   if("".equals(keyWord)){

							   respContent = "���������쳣";
							   textMessage.setContent(respContent);
							   respMessage = MessageUtil.textMessageToXml(textMessage);

						    }else{

						     String init = keyWord.substring(0, keyWord.indexOf("~"));
						     String dest = keyWord.substring(keyWord.indexOf("~")+1, keyWord.indexOf("/"));
						     String date = keyWord.substring(keyWord.indexOf("/")+1);
						     System.out.println(init + dest+date);

						     //respContent = Functions.searchTrainTicket(init, dest, date);
						      NewsMessage newsMessage = new NewsMessage();
	 						  newsMessage.setToUserName(fromUserName);
	 					      newsMessage.setFromUserName(toUserName);
	 						  newsMessage.setCreateTime(new Date().getTime());
	 						  newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
	 						  newsMessage.setFuncFlag(0);
	 						   ArrayList<Article> articleList = new ArrayList<Article>();
	 						    Article article1 = new Article();
								article1.setTitle("��"+init+"��"+dest+"����Ʊ��Ϣ���£�");
								article1.setDescription("");
								article1.setPicUrl("");
								article1.setUrl("");
								articleList.add(article1);
	 						  ArrayList<Article> al = Functions.searchTrainTicket(init, dest, date);
	 						  for(int i=0;i<al.size();i++){
	 							  articleList.add(al.get(i));
	 						  }
	 						  newsMessage.setArticleCount(articleList.size());
							  newsMessage.setArticles(articleList);
							  respMessage = MessageUtil.newsMessageToXml(newsMessage);

						    }

				      }

                       else if (content.startsWith("�Ź�")){

				    	  String keyWord = content.replaceAll("^�Ź�", "").trim();
				    	  NewsMessage newsMessage = new NewsMessage();
 						  newsMessage.setToUserName(fromUserName);
 					      newsMessage.setFromUserName(toUserName);
 						  newsMessage.setCreateTime(new Date().getTime());
 						  newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
 						  newsMessage.setFuncFlag(0);
				    	  Session session = null;


				    	  try{

				 				//session = HibernateUtil.getSession();
				 				session=Hibernate.getSessionByDB("mysql");
				 				session.beginTransaction();
				 			    String hql="from WechatUser w where w.FromUserName=?";
				 				Query query = session.createQuery(hql).setParameter(0, fromUserName);
				 				WechatUser wechatUser=(WechatUser)query.uniqueResult();
				 				String Location_X = wechatUser.getLocation_X();
				 				String Location_Y = wechatUser.getLocation_Y();
				 				ArrayList<Article> articleList = new ArrayList<Article>();
				 				if("".equals(keyWord)){

							    respContent = "��������������";
				 				}
				 				else if("1".equals(keyWord)){


				 				  articleList  = Groupon.SearchGroupon(Location_X, Location_Y, "�Ƶ�");
				 				  if(articleList.size()==0){
				 					  respContent ="����û�������Ź�";
				 					  textMessage.setContent(respContent);
									  respMessage = MessageUtil.textMessageToXml(textMessage);
				 				  }
				 				  else{

				 					 newsMessage.setArticleCount(articleList.size());
									 newsMessage.setArticles(articleList);
									 respMessage = MessageUtil.newsMessageToXml(newsMessage);

				 				  }
				 				}
				 				else if("2".equals(keyWord)){

				 				 articleList  = Groupon.SearchGroupon(Location_X, Location_Y, "��ʳ");

				 				 if(articleList.size()==0){
				 					  respContent ="����û�������Ź�";
				 					  textMessage.setContent(respContent);
									  respMessage = MessageUtil.textMessageToXml(textMessage);
				 				  }
				 				  else{
				 					 newsMessage.setArticleCount(articleList.size());
									 newsMessage.setArticles(articleList);
									 respMessage = MessageUtil.newsMessageToXml(newsMessage);

				 				  }

					 			}
				 				else if("3".equals(keyWord)){

				 				  articleList  = Groupon.SearchGroupon(Location_X, Location_Y, "����");
				 				 if(articleList.size()==0){
				 					  respContent ="����û�������Ź�";
				 					  textMessage.setContent(respContent);
									  respMessage = MessageUtil.textMessageToXml(textMessage);
				 				  }
				 				  else{
				 					 newsMessage.setArticleCount(articleList.size());
									 newsMessage.setArticles(articleList);
									 respMessage = MessageUtil.newsMessageToXml(newsMessage);

				 				  }

					 			}

								 session.getTransaction().commit();

							  }catch(Exception e){

									e.printStackTrace();
									session.getTransaction().rollback();

								}finally{
									Hibernate.closeSession("mysql");
								}

				    }
                       else if (content.startsWith("����")){

 				    	  String keyWord = content.replaceAll("^����", "").trim();
 				    	  NewsMessage newsMessage = new NewsMessage();
 						  newsMessage.setToUserName(fromUserName);
 					      newsMessage.setFromUserName(toUserName);
 						  newsMessage.setCreateTime(new Date().getTime());
 						  newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
 						  newsMessage.setFuncFlag(0);

 						  Session session = null;
 				    	  try{

 				 				session = Hibernate.getSessionByDB("mysql");
 				 				session.beginTransaction();
 				 			    String hql="from WechatUser w where w.FromUserName=?";
 				 				Query query = session.createQuery(hql).setParameter(0, fromUserName);
 				 				WechatUser wechatUser=(WechatUser)query.uniqueResult();
 				 				String Location_X = wechatUser.getLocation_X();
 				 				String Location_Y = wechatUser.getLocation_Y();
 				 				ArrayList<Article> articleList = new ArrayList<Article>();

 				 				if("".equals(keyWord)){

 							    respContent = "��������������";
 				 				}
 				 				else if("����".equals(keyWord)||"��ʳ".equals(keyWord)){

 				 					Article article1 = new Article();
									article1.setTitle("�����ġ����������£�");
									article1.setDescription("");
									article1.setPicUrl("http://img2.imgtn.bdimg.com/it/u=959692041,2389291919&fm=21&gp=0.jpg");
									article1.setUrl("");

									articleList.add(article1);

									ArrayList<Article> al =SearchAround.SearchGroupon("��ʳ", Location_X, Location_Y);
									for(int i=0;i<al.size();i++){

										articleList.add(al.get(i));
									}
 				 				}
 				 				else if("�Ƶ�".equals(keyWord)||"����".equals(keyWord)){
			 					    Article article1 = new Article();
									article1.setTitle("�����ġ��Ƶ꡿���£�");
									article1.setDescription("");
									article1.setPicUrl("http://img4.imgtn.bdimg.com/it/u=4028728523,1686892755&fm=23&gp=0.jpg");
									article1.setUrl("");
									articleList.add(article1);

								    ArrayList<Article> al  = SearchAround.SearchGroupon("�Ƶ�", Location_X, Location_Y);
                                   for(int i=0;i<al.size();i++){

										articleList.add(al.get(i));
									}
  				 			    }
 				 				else if("����".equals(keyWord)){
			 					    Article article1 = new Article();
									article1.setTitle("�����ġ����㡿���£�");
									article1.setDescription("");
									article1.setPicUrl("http://img2.imgtn.bdimg.com/it/u=4254965783,2178102199&fm=23&gp=0.jpg");
									article1.setUrl("");
									articleList.add(article1);

								    ArrayList<Article> al  = BaiduMapService.searchLocation("����", Location_X, Location_Y);
                                   for(int i=0;i<al.size();i++){

										articleList.add(al.get(i));
									}
  				 			    }
 				 				else if("����".equals(keyWord)){
			 					    Article article1 = new Article();
									article1.setTitle("�����ġ����С����£�");
									article1.setDescription("");
									article1.setPicUrl("http://www.sooshong.com/picture/suzhoutongji/5096572012525150949.jpg");
									article1.setUrl("");
									articleList.add(article1);

								    ArrayList<Article> al  = SearchAround.SearchGroupon("����", Location_X, Location_Y);
                                   for(int i=0;i<al.size();i++){

										articleList.add(al.get(i));
									}
  				 			    }

		                          else if("����".equals(keyWord)){

		 				 			 String Responce = Functions.searchLocation("����",Location_X,Location_Y);

		 									try{

		 									  Document doc = DocumentHelper.parseText(Responce);
		 									  Element root = doc.getRootElement();
		 									  String status = root.element("status").getText();

		 									  if(status!=null&&status.equals("0"))
		 									  {

		 										 List<Element> resEle = root.element("results").elements();
		 										  articleList = new ArrayList<Article>();

		 	                                        Article article1 = new Article();
		 											article1.setTitle("�����ġ����������£�");
		 											article1.setDescription("");
		 											article1.setPicUrl("http://img0.imgtn.bdimg.com/it/u=3675380918,3627544226&fm=23&gp=0.jpg");
		 											article1.setUrl("");
		 											articleList.add(article1);

		 											if(resEle.size()>10){

		 		 						                 for(int i=0;i<10;i++){

		 		 										   articleList.add( new Article("�� "+resEle.get(i).elementText("name")+" ��"+"<"+resEle.get(i).element("detail_info").elementText("distance")+"M>"
		 												               +resEle.get(i).elementText("address"),"","",resEle.get(i).element("detail_info").elementText("detail_url")));
		 		 									    }
		 		 						                }else{

		 		 						                 for(int i=0;i<resEle.size();i++){

		 		  										  articleList.add(new Article("�� "+resEle.get(i).elementText("name")+" ��"+"<"+resEle.get(i).element("detail_info").elementText("distance")+"M>"
		 		 										               +resEle.get(i).elementText("address"),"","",resEle.get(i).element("detail_info").elementText("detail_url")));

		 		 									      }
		 		 						                }
                	 							     }
		 									  }  catch(DocumentException e){
		 										  e.printStackTrace();
		 									  }


		 					 			}
 				 				  else if("ATM".equals(keyWord)||"atm".equals(keyWord)){

 				 					String Responce = Functions.searchLocation("ATM",Location_X,Location_Y);

 									try{

 									  Document doc = DocumentHelper.parseText(Responce);
 									  Element root = doc.getRootElement();
 									  String status = root.element("status").getText();

 									  if(status!=null&&status.equals("0"))
 									  {

 										 List<Element> resEle = root.element("results").elements();
 										 articleList = new ArrayList<Article>();

 	                                        Article article1 = new Article();
 											article1.setTitle("�����ġ�ATM�����£�");
 											article1.setDescription("");
 											article1.setPicUrl("");
 											article1.setUrl("");
 											articleList.add(article1);

 											if(resEle.size()>10){

 		 						                 for(int i=0;i<10;i++){

 		 										   articleList.add( new Article("�� "+resEle.get(i).elementText("name")+" ��"+"<"+resEle.get(i).element("detail_info").elementText("distance")+"M>"
 												               +resEle.get(i).elementText("address"),"","",resEle.get(i).element("detail_info").elementText("detail_url")));
 		 									    }
 		 						                }else{

 		 						                 for(int i=0;i<resEle.size();i++){

 		  										  articleList.add(new Article("�� "+resEle.get(i).elementText("name")+" ��"+"<"+resEle.get(i).element("detail_info").elementText("distance")+"M>"
 		 										               +resEle.get(i).elementText("address"),"","",resEle.get(i).element("detail_info").elementText("detail_url")));

 		 									      }
 		 						             }

 							            }
 									  }  catch(DocumentException e){
 										  e.printStackTrace();
 									  }

 					 			}

							    newsMessage.setArticleCount(articleList.size());
								newsMessage.setArticles(articleList);
								respMessage = MessageUtil.newsMessageToXml(newsMessage);
 								session.getTransaction().commit();

 							  }catch(Exception e){

 									e.printStackTrace();
 									session.getTransaction().rollback();

 								}finally{
 									Hibernate.closeSession("mysql");;
 								}

 				       }
			     }
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {

				 String Location_X = requestMap.get("Location_X");
				 String Location_Y = requestMap.get("Location_Y");
				 String Label = requestMap.get("Label");
				 Session session = null;

			try{

				session = Hibernate.getSessionByDB("mysql");
				session.beginTransaction();
			    String hql="select count(*)from WechatUser w where w.FromUserName=?";
				Query query = session.createQuery(hql).setParameter(0, fromUserName);
				long count=(Long)query.uniqueResult();
				WechatUser wechatUser=new WechatUser();

			   if(count>0){

				String hql1="from WechatUser w where w.FromUserName=?";
				Query query1 = session.createQuery(hql1).setParameter(0, fromUserName);
				WechatUser wechatUser1=(WechatUser)query1.uniqueResult();
				wechatUser1.setLocation_X(Location_X);
				wechatUser1.setLocation_Y(Location_Y);
				wechatUser1.setLabel(Label);
				session.save(wechatUser1);

				}else{

					wechatUser.setFromUserName(fromUserName);
					wechatUser.setLocation_X(Location_X);
					wechatUser.setLocation_Y(Location_Y);
					wechatUser.setLabel(Label);
					session.save(wechatUser);

				}

				session.getTransaction().commit();

				}catch(Exception e){

					e.printStackTrace();
					session.getTransaction().rollback();
				}finally{
					Hibernate.closeSession("mysql");
				}

		     }

			// ͼƬ��Ϣ
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {

				respContent = "�����͵���ͼƬ��Ϣ��";
			}

			// ������Ϣ
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
				respContent = "�����͵���������Ϣ��";
			}
			// ��Ƶ��Ϣ
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {

				respContent = "�����͵�����Ƶ��Ϣ��";
			}
			// �¼�����
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				// �¼�����
				String eventType = requestMap.get("Event");
				// ����
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {

					  respContent = "лл���Ĺ�ע��";
					  textMessage.setContent(respContent);
					  respMessage = MessageUtil.textMessageToXml(textMessage);
				}


				// �Զ����˵������¼�
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					// �¼�KEYֵ���봴���Զ����˵�ʱָ����KEYֵ��Ӧ
					String eventKey = requestMap.get("EventKey");
					NewsMessage newsMessage = new NewsMessage();
					newsMessage.setToUserName(fromUserName);
				    newsMessage.setFromUserName(toUserName);
					newsMessage.setCreateTime(new Date().getTime());
					newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
					newsMessage.setFuncFlag(0);

					if (eventKey.equals("11")) {

					    respContent = "��ѯ�𳵳�����Ϣ�뷢�� :\n ��������+�г���~���ڣ�����������K138~2015/03/28";
						textMessage.setContent(respContent);
						respMessage = MessageUtil.textMessageToXml(textMessage);

					} else if (eventKey.equals("12")) {

						respContent = "��ѯ������Ϣ�뷢�� :\n  ��������+������~���ڣ�����������CZ3128~2015/03/28";
						textMessage.setContent(respContent);
						respMessage = MessageUtil.textMessageToXml(textMessage);
					} else if (eventKey.equals("13")) {

					    respContent ="��ѯ���Ϻ�������վʼ����������Ϣ�뷢�� :\n ��������+Ŀ�ĳ��У���������������";
					    textMessage.setContent(respContent);
						respMessage = MessageUtil.textMessageToXml(textMessage);

					} else if (eventKey.equals("14")) {


					} else if (eventKey.equals("15")) {


					}
					else if (eventKey.equals("21")) {

						ArrayList<Article> al = SearchWeather.searchWeather("�Ϻ�");
					    List<Article> articleList = new ArrayList<Article>();
					    Article article1 = new Article();
						article1.setTitle("�Ϻ�����Ԥ��");
						article1.setDescription("");
						article1.setPicUrl("");
						article1.setUrl("");
						articleList.add(article1);

					    for(int i=0;i<al.size();i++){

	      				articleList.add(new Article(al.get(i).getTitle(),"",al.get(i).getPicUrl(),""));

					    }
					    newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respMessage = MessageUtil.newsMessageToXml(newsMessage);

					}
					else if (eventKey.equals("22")) {

						StringBuffer buffer = new StringBuffer();
						buffer.append("�ܱ�����ʹ��˵��").append("\n");
						buffer.append("1)���͵���λ��").append("\n\n");
						buffer.append("2)ָ���ؼ���������ʽ������+�ؼ��� ���磺������ʳ���������ݡ�����ATM������KTV������������");

						respContent = buffer.toString();
						textMessage.setContent(respContent);
						respMessage = MessageUtil.textMessageToXml(textMessage);

				    }

					else if (eventKey.equals("23")) {

						StringBuffer buffer = new StringBuffer();
						buffer.append("���ã����ظ�����ѡ��������").append("\n");
						buffer.append("�Ź�1. �ܱ���ʳ").append("\n");
						buffer.append("�Ź�2. �ܱ߾Ƶ�").append("\n");
						buffer.append("�Ź�3. ���ξ���").append("\n");

						respContent = buffer.toString();
						textMessage.setContent(respContent);
						respMessage = MessageUtil.textMessageToXml(textMessage);

					}
					else if (eventKey.equals("24")) {

						respContent = "ͣ��λԤ��";
						textMessage.setContent(respContent);
						respMessage = MessageUtil.textMessageToXml(textMessage);
					}

					else if (eventKey.equals("25")){

						ArrayList<ArticleList> al = DisplayInfo.searchInfo();
					    List<Article> articleList = new ArrayList<Article>();
					    Article article1 = new Article();
						article1.setTitle("���Ź��һ�չ����չ���У�");
						article1.setDescription("");
						article1.setPicUrl("http://www.cnena.com/upload_files/qb_news_/49/6928_1401162038_838013.jpg");
						article1.setUrl("");
						articleList.add(article1);

					    for(int i=1;i<10;i++){

	      				  articleList.add(new Article(al.get(i).getTitle(),"","",al.get(i).getURLs()));

					    }
					    newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respMessage = MessageUtil.newsMessageToXml(newsMessage);

					}

					else if (eventKey.equals("31")) {

						 respContent = Functions.RoadCondition();
						 textMessage.setContent(respContent);
						 respMessage = MessageUtil.textMessageToXml(textMessage);

					} else if (eventKey.equals("32")) {

						 respContent = "Fuck";
						 textMessage.setContent(respContent);
						 respMessage = MessageUtil.textMessageToXml(textMessage);


					} else if (eventKey.equals("33")) {

						List<Article> articleList = new ArrayList<Article>();
						Article article = new Article();
						article.setTitle("�Ϻ����ι���");
						article.setDescription("���ϰ߲���ʯ���ţ��߽ű������Ȱɺ��ϳ�Ƭ�ﶼ���������޵��Ϻ��飬ȥ����С��¥��С����������ƷƷ�ص��Ϻ����е��Ϻ�ζ��");
						article.setPicUrl("http://img4.imgtn.bdimg.com/it/u=2619572761,627349077&fm=21&gp=0.jpg");
						article.setUrl(Functions.searchTravel());

	      				articleList.add(article);
						newsMessage.setArticleCount(articleList.size());
						newsMessage.setArticles(articleList);
						respMessage = MessageUtil.newsMessageToXml(newsMessage);

					}
                     else if (eventKey.equals("34")) {


					}

				}

			}


		} catch (Exception e) {

			e.printStackTrace();
		}

		   return respMessage;
	}

}
