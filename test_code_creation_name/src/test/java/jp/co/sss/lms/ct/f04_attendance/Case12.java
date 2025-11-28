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
 * ケース12
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース12 受講生 勤怠直接編集 入力チェック")
public class Case12 {

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
	@DisplayName("テスト05 不適切な内容で修正してエラー表示：出勤（時）が空白")
	void test05() {

		// 一番上の出勤（時）を取得し未入力にする
		final WebElement startHourError = webDriver.findElement(By.id("startHour0"));
		Select selectError = new Select(startHourError);
		selectError.selectByIndex(0);
		selectError.selectByVisibleText("");

		// ボタン押下前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		// ページ最下部までスクロール
		scrollTo("1000");

		// 更新ボタン押下のエビデンス取得
		getEvidence(new Object() {
		}, "updateBtn");

		// 更新ボタンを取得して押下
		final WebElement submitBtn = webDriver.findElement(By.cssSelector("input[value='更新']"));
		submitBtn.click();

		// アラート受け入れ処理
		acceptAlertIfPresent(webDriver);
	
		// 遷移後のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// エラーメッセージを取得し一致しているかの確認
		String errorMsg = webDriver.findElement(By.className("error")).getText();
		assertEquals("* 出勤時間が正しく入力されていません。", errorMsg);

		// 一番上の出勤（時）を取得し"09"を入力する
		final WebElement startHour = webDriver.findElement(By.id("startHour0"));
		Select select = new Select(startHour);
		select.selectByIndex(0);
		select.selectByVisibleText("09");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正してエラー表示：出勤（分）が空白")
	void test06() {

		// 一番上の出勤（分）を取得し未入力にする
		final WebElement startMinuteError = webDriver.findElement(By.id("startMinute0"));
		Select selectError = new Select(startMinuteError);
		selectError.selectByIndex(0);
		selectError.selectByVisibleText("");

		// ボタン押下前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		// ページ最下部までスクロール
		scrollTo("1000");

		// 更新ボタン押下のエビデンス取得
		getEvidence(new Object() {
		}, "updateBtn");

		// 更新ボタンを取得して押下
		final WebElement submitBtn = webDriver.findElement(By.cssSelector("input[value='更新']"));
		submitBtn.click();

		// アラート受け入れ処理
		acceptAlertIfPresent(webDriver);

		// 遷移後のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// エラーメッセージを取得し一致しているかの確認
		String errorMsg = webDriver.findElement(By.className("error")).getText();
		assertEquals("* 出勤時間が正しく入力されていません。", errorMsg);

		// 一番上の出勤（分）を取得し"00"を入力する
		final WebElement startMInute = webDriver.findElement(By.id("startMinute0"));
		Select select = new Select(startMInute);
		select.selectByIndex(0);
		select.selectByVisibleText("00");
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正してエラー表示：退勤（時）が空白")
	void test07() {

		// 一番上の退勤（時）を取得し未入力にする
		final WebElement endHourError = webDriver.findElement(By.id("endHour0"));
		Select selectErroe = new Select(endHourError);
		selectErroe.selectByIndex(0);
		selectErroe.selectByVisibleText("");

		// ボタン押下前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		// ページ最下部までスクロール
		scrollTo("1000");

		// 更新ボタン押下のエビデンス取得
		getEvidence(new Object() {
		}, "updateBtn");

		// 更新ボタンを取得して押下
		final WebElement submitBtn = webDriver.findElement(By.cssSelector("input[value='更新']"));
		submitBtn.click();

		// アラート受け入れ処理
		acceptAlertIfPresent(webDriver);

		// 遷移後のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// エラーメッセージを取得し一致しているかの確認
		String errorMsg = webDriver.findElement(By.className("error")).getText();
		assertEquals("* 退勤時間が正しく入力されていません。", errorMsg);

		// 一番上の退勤（時）を取得し"18"を入力する
		final WebElement endHour = webDriver.findElement(By.id("endHour0"));
		Select select = new Select(endHour);
		select.selectByIndex(0);
		select.selectByVisibleText("18");
	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正してエラー表示：退勤（分）が空白")
	void test08() {

		// 一番上の退勤（分）を取得し未入力にする
		final WebElement endMinuteError = webDriver.findElement(By.id("endMinute0"));
		Select selectError = new Select(endMinuteError);
		selectError.selectByIndex(0);
		selectError.selectByVisibleText("");

		// ボタン押下前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		// ページ最下部までスクロール
		scrollTo("1000");

		// 更新ボタン押下のエビデンス取得
		getEvidence(new Object() {
		}, "updateBtn");

		// 更新ボタンを取得して押下
		final WebElement submitBtn = webDriver.findElement(By.cssSelector("input[value='更新']"));
		submitBtn.click();

		// アラート受け入れ処理
		acceptAlertIfPresent(webDriver);

		// 遷移後のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// エラーメッセージを取得し一致しているかの確認
		String errorMsg = webDriver.findElement(By.className("error")).getText();
		assertEquals("* 退勤時間が正しく入力されていません。", errorMsg);

		// 一番上の退勤（分）を取得し"00"を入力する
		final WebElement endMinute = webDriver.findElement(By.id("endMinute0"));
		Select select = new Select(endMinute);
		select.selectByIndex(0);
		select.selectByVisibleText("00");
	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正してエラー表示：出勤が未入力で退勤に入力有")
	void test09() {

		// 一番上の出勤（時）を取得し未入力にする
		final WebElement startHourError = webDriver.findElement(By.id("startHour0"));
		Select selectHourError = new Select(startHourError);
		selectHourError.selectByIndex(0);
		selectHourError.selectByVisibleText("");

		// 一番上の出勤（分）を取得し未入力にする
		final WebElement startMinuteError = webDriver.findElement(By.id("startMinute0"));
		Select selectMinuteError = new Select(startMinuteError);
		selectMinuteError.selectByIndex(0);
		selectMinuteError.selectByVisibleText("");

		// ボタン押下前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		// ページ最下部までスクロール
		scrollTo("1000");

		// 更新ボタン押下のエビデンス取得
		getEvidence(new Object() {
		}, "updateBtn");

		// 更新ボタンを取得して押下
		final WebElement submitBtn = webDriver.findElement(By.cssSelector("input[value='更新']"));
		submitBtn.click();

		// アラート受け入れ処理
		acceptAlertIfPresent(webDriver);

		// 遷移後のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// エラーメッセージを取得し一致しているかの確認
		String errorMsg = webDriver.findElement(By.className("error")).getText();
		assertEquals("* 出勤情報がないため退勤情報を入力出来ません。", errorMsg);

	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正してエラー表示：出勤が退勤よりも遅い時間")
	void test10() {

		// キーの取得
		Map<By, String> inputErrorData = new LinkedHashMap<>();
		inputErrorData.put(By.id("startHour0"), "18");
		inputErrorData.put(By.id("startMinute0"), "00");
		inputErrorData.put(By.id("endHour0"), "09");
		inputErrorData.put(By.id("endMinute0"), "00");

		// キー入力
		for (Map.Entry<By, String> entry : inputErrorData.entrySet()) {
			final WebElement element = visibility(entry.getKey(), 10);
			Select select = new Select(element);
			select.selectByIndex(0);
			select.selectByVisibleText(entry.getValue());
		}

		// ボタン押下前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		// ページ最下部までスクロール
		scrollTo("1000");

		// 更新ボタン押下のエビデンス取得
		getEvidence(new Object() {
		}, "updateBtn");

		// 更新ボタンを取得して押下
		final WebElement submitBtn = webDriver.findElement(By.cssSelector("input[value='更新']"));
		submitBtn.click();

		// アラート受け入れ処理
		acceptAlertIfPresent(webDriver);

		// 遷移後のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// エラーメッセージを取得し一致しているかの確認
		String errorMsg = webDriver.findElement(By.className("error")).getText();
		assertEquals("* 退勤時刻[0]は出勤時刻[0]より後でなければいけません。", errorMsg);

		// キーの取得
		Map<By, String> inputData = new LinkedHashMap<>();
		inputData.put(By.id("startHour0"), "09");
		inputData.put(By.id("startMinute0"), "00");
		inputData.put(By.id("endHour0"), "12");
		inputData.put(By.id("endMinute0"), "00");

		// キー入力
		for (Map.Entry<By, String> entry : inputData.entrySet()) {
			final WebElement element = visibility(entry.getKey(), 10);
			Select select = new Select(element);
			select.selectByIndex(0);
			select.selectByVisibleText(entry.getValue());
		}
	}

	@Test
	@Order(11)
	@DisplayName("テスト11 不適切な内容で修正してエラー表示：出退勤時間を超える中抜け時間")
	void test11() {

		// 一番上の中抜け時間を取得し"4時間"を入力する
		final WebElement blankTimeError = webDriver.findElement(By.name("attendanceList[0].blankTime"));
		Select selectError = new Select(blankTimeError);
		selectError.selectByIndex(0);
		selectError.selectByVisibleText("4時間");

		// ボタン押下前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		// ページ最下部までスクロール
		scrollTo("1000");

		// 更新ボタン押下のエビデンス取得
		getEvidence(new Object() {
		}, "updateBtn");

		// 更新ボタンを取得して押下
		final WebElement submitBtn = webDriver.findElement(By.cssSelector("input[value='更新']"));
		submitBtn.click();

		// アラート受け入れ処理
		acceptAlertIfPresent(webDriver);

		// 遷移後のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// エラーメッセージを取得し一致しているかの確認
		String errorMsg = webDriver.findElement(By.className("error")).getText();
		assertEquals("* 中抜け時間が勤務時間を超えています。", errorMsg);

		// 一番上の中抜け時間を取得し未入力にする
		final WebElement blankTime = webDriver.findElement(By.name("attendanceList[0].blankTime"));
		Select select = new Select(blankTime);
		select.selectByIndex(0);
		select.selectByVisibleText("");
	}

	@Test
	@Order(12)
	@DisplayName("テスト12 不適切な内容で修正してエラー表示：備考が100文字超")
	void test12() {

		// 一番上の備考を取得し、101文字入力する
		final WebElement noteError = webDriver.findElement(By.name("attendanceList[0].note"));
		noteError.clear();
		String str = "あ";
		noteError.sendKeys(str.repeat(101));

		// ボタン押下前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		// ページ最下部までスクロール
		scrollTo("1000");

		// 更新ボタン押下のエビデンス取得
		getEvidence(new Object() {
		}, "updateBtn");

		// 更新ボタンを取得して押下
		final WebElement submitBtn = webDriver.findElement(By.cssSelector("input[value='更新']"));
		submitBtn.click();

		// アラート受け入れ処理
		acceptAlertIfPresent(webDriver);

		// 遷移後のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// エラーメッセージを取得し一致しているかの確認
		String errorMsg = webDriver.findElement(By.className("error")).getText();
		assertEquals("* 備考の長さが最大値(100)を超えています。", errorMsg);
		
		// 一番上の備考を取得し、未入力にする
		final WebElement note = webDriver.findElement(By.name("attendanceList[0].note"));
		note.clear();
		note.sendKeys("");
	}

}
