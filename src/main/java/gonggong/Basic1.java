package gonggong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

//Detail.java 프로그램 작성하기
public class Basic1 {
	public static void main(String[] args) throws IOException {
		String gongurl = "http://apis.data.go.kr/9720000/searchservice/basic";
		//URLEncoder.ecnode : 2바이트 문자열을 utf-8 형식으로 인코딩
		String search = URLEncoder.encode("자료명,홍길동","UTF-8");
		StringBuilder urlBuilder = new StringBuilder(gongurl); //*URL*
		urlBuilder.append("?serviceKey=DQuv90uLevpCXTC0lNdsyXUnQaW5eyVgqsAezWdbxT7fJ7n%2BQzT12SE1lgp3cuthYLGFqruFrmWLCFyN0B15yA%3D%3D");
		urlBuilder.append("&displaylines=10&search="+search);
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // 공공데이터 접속
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <=300) { //정상처리
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		System.out.println(sb.toString());
		//sb : 국회 도서자료의 응답 메세지 저장. xml 형태로 저장됨. 응답형식이 xml임.
		//Jsoup : markup language 파싱기능을 가진 툴. html 분석. xml 분석.
		//doc : sb 문자열의 내용(xml 형식)을 DOM tree로 변경.
		Document doc = Jsoup.parse(sb.toString());
		// recode 태그 들
		Elements recodes = doc.select("recode");
		for (Element r : recodes) {
			//r : recode 태그 한 개
			//r.select("item") : recode 태그의 하위 item 태그들
			//i : item 태그 한 개
			for(Element i : r.select("item")) {
				String name = i.select("name").html();
				String value = i.select("value").html();
				System.out.print(name + " : " + value + "\t");
			}
			System.out.println();
		}
	}
}
