package jp.co.sss.lms.ct.f04_attendance;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト 勤怠管理機能
 * ケース11
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース11 受講生 勤怠直接編集 正常系")
public class Case11 {

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
	@DisplayName("テスト04 「勤怠情報を直接編集する」リンクから勤怠情報直接変更画面に遷移")
	void test04() {

		// リンクを取得し押下
		final WebElement attendanceEdit = webDriver.findElement(By.cssSelector("a[href='/lms/attendance/update']"));
		attendanceEdit.click();

		// エビデンス取得
		getEvidence(new Object() {
		});

		// 遷移先URLの一致確認
		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/attendance/update", url);
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 すべての研修日程の勤怠情報を正しく更新し勤怠管理画面に遷移")
	void test05() {

		// 対象箇所までスクロール
		scrollTo("1000");
		
		// 待ち処理
		pageLoadTimeout(10);

		// キーの取得
		Map<By, String> inputData = new LinkedHashMap<>();
		inputData.put(By.id("startHour7"), "09");
		inputData.put(By.id("startMinute7"), "00");
		inputData.put(By.id("endHour7"), "18");
		inputData.put(By.id("endMinute7"), "00");
		
		// キー入力
		for(Map.Entry<By, String> entry : inputData.entrySet()) {
			final WebElement element = visibility(entry.getKey(), 10);
			Select select = new Select(element);
			select.selectByIndex(0);
			select.selectByVisibleText(entry.getValue());
		}

		// エビデンス取得
		getEvidence(new Object() {
		}, "before");

		// 更新ボタンを取得して押下
		final WebElement submitBtn = webDriver.findElement(By.cssSelector("input[value='更新']"));
		submitBtn.click();

		// アラート受け入れ処理
		acceptAlertIfPresent(webDriver);
		
		// 待ち処理
		visibilityTimeout(By.className("close"), 10);

		// エビデンス取得
		getEvidence(new Object() {
		}, "after");

		// 遷移先画面の一致確認
		String text = webDriver.findElement(By.xpath("//span[contains(text(),'完了')]")).getText();
		assertEquals("勤怠情報の登録が完了しました。", text);
	}

}
