package gonggong;



import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MovieInfo {
	public static void main(String[] args) throws Exception {
		String key = "f5eef3421c602c6cb7ea224104795888";
		String code = "20232121";
		String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/"
				+ "searchMovieInfo.xml?key="+key+"&movieCd="+code;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		//url : api를 통해 xml 형식에 데이터를 전송해 주는 주소 url
		//doc : xml 형식 문석의 문서노드(루트노드)
		Document doc = dBuilder.parse(url);
		doc.getDocumentElement().normalize(); //루트 태그로 시작
		//getElementsByTagName : 태그 이름으로 검색. DOM 관련 함수.
		NodeList nList = doc.getElementsByTagName("movieInfo");
		for(int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			Element eElement = (Element) nNode;
			System.out.println("영화코드 : " + getTagvalue("movieCd", eElement));
			System.out.println("영화명(한글) : " + getTagvalue("movieNm", eElement));
			System.out.println("영화명(영문) : " + getTagvalue("movieNmEn", eElement));
			System.out.println("재생시간 : " + getTagvalue("showTm", eElement));
			System.out.println("개봉일 : " + getTagvalue("openDt", eElement));
			System.out.println("영화유형 : " + getTagvalue("typeNm", eElement));
			System.out.println("제작국가명 : " + getTagvalue("nationNm", eElement));
			System.out.println("장르 : " + getTagvalue("genres","genre", eElement));
			System.out.println("감독명 : " + getTagvalue("directors", "director",eElement));
			System.out.println("출연배우 : " + getTagvalue("actors", "actor",eElement));
		}
		
	}


	private static String getTagvalue(String tag, Element eElement) {
		String result = "";
		//getChildNodes() : 현재 태그의 하위 태그들.
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		result = nlList.item(0).getTextContent(); //문자 내용
		return result;
	}
	private static String getTagvalue(String tag, String childTag, Element eElement) {
		String result = "";
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		for(int i = 0 ; i < eElement.getElementsByTagName(childTag).getLength(); i++) {
			result += nlList.item(i).getChildNodes().item(0).getTextContent() + " ";
		}
		return result;
	}
}
