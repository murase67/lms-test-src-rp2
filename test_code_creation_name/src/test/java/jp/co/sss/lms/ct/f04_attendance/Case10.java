package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.thymeleaf.util.StringUtils;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト 勤怠管理機能
 * ケース10
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース10 受講生 勤怠登録 正常系")
public class Case10 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// 遷移先URL
		goTo("http://localhost:8080/lms");

		// ページタイトルの一致確認
		String title = WebDriverUtils.webDriver.getTitle();
		assertEquals("ログイン | LMS", title);

		// エビデンス取得
		getEvidence(new Object() {
		});
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// 値の取得
		final WebElement login = webDriver.findElement(By.className("btn-primary"));
		final WebElement loginId = webDriver.findElement(By.id("loginId"));
		final WebElement password = webDriver.findElement(By.id("password"));

		// キー入力
		loginId.clear();
		loginId.sendKeys("StudentAA01");
		password.clear();
		password.sendKeys("studentAA01");

		// ログイン前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		// ログインボタンをクリック
		login.click();

		// ログイン後のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// 遷移先URLの一致確認
		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/course/detail", url);
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「勤怠」リンクから勤怠管理画面に遷移")
	void test03() {

		// リンクを取得し押下
		final WebElement attendance = webDriver.findElement(By.cssSelector("a[href='/lms/attendance/detail']"));
		attendance.click();

		// アラート受け入れ処理
		acceptAlertIfPresent(webDriver);

		// エビデンス取得
		getEvidence(new Object() {
		});

		// 遷移先URLの一致確認
		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/attendance/detail", url);
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「出勤」ボタンを押下し出勤時間を登録")
	void test04() {

		// ボタンを取得して押下
		final WebElement startTime = webDriver.findElement(By.cssSelector("input[value='出勤']"));
		startTime.click();

		// アラート受け入れ処理
		acceptAlertIfPresent(webDriver);
		
		// エビデンス取得
		getEvidence(new Object() {
		});

		// テーブルを取得
		List<WebElement> rows = webDriver.findElements(By.xpath("//*[@id=\"main\"]/div/table//td"));

		// 本日の日付(2025年11月28日(金))の出勤欄が入力されているか確認
		boolean found = false;
		for (WebElement row : rows) {
			String startTimeText = row.findElement(By.xpath("//*[@id=\"main\"]/div[2]/div/table/tbody/tr[2]/td[3]"))
					.getText();
			if (StringUtils.isEmpty(startTimeText)) {
			} else {
				found = true;
				break;
			}
			assertTrue(found);
		}

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 「退勤」ボタンを押下し退勤時間を登録")
	void test05() {
		
		// ボタンを取得して押下
		final WebElement endTime = webDriver.findElement(By.cssSelector("input[value='退勤']"));
		endTime.click();
		
		// アラート受け入れ処理
		acceptAlertIfPresent(webDriver);

		// エビデンス取得
		getEvidence(new Object() {
		});

		// テーブルを取得
		List<WebElement> rows = webDriver.findElements(By.xpath("//*[@id=\"main\"]/div/table//td"));

		// 本日の日付(2025年11月28日(金))の退勤欄が入力されているか確認
		boolean found = false;
		for (WebElement row : rows) {
			String endTimeText = row.findElement(By.xpath("//*[@id=\"main\"]/div[2]/div/table/tbody/tr[2]/td[4]"))
					.getText();
			if (StringUtils.isEmpty(endTimeText)) {
			} else {
				found = true;
				break;
			}
			assertTrue(found);
		}
	}

}
