package jp.co.sss.lms.ct.f06_login2;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.Assert.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト ログイン機能②
 * ケース16
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース16 受講生 初回ログイン 変更パスワード未入力")
public class Case16 {

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
	@DisplayName("テスト02 DBに初期登録された未ログインの受講生ユーザーでログイン")
	void test02() {
		// 値の取得
		final WebElement login = webDriver.findElement(By.className("btn-primary"));
		final WebElement loginId = webDriver.findElement(By.id("loginId"));
		final WebElement password = webDriver.findElement(By.id("password"));

		// キー入力
		loginId.clear();
		loginId.sendKeys("StudentAA03");
		password.clear();
		password.sendKeys("StudentAA03");

		// ログイン前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		// ログインボタンをクリック
		login.click();

		// ログイン後のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// 遷移先のURLの一致確認
		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/user/agreeSecurity", url);
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 「同意します」チェックボックスにチェックを入れ「次へ」ボタン押下")
	void test03() {

		// チェックボックスを取得してチェックを入れる
		final WebElement agreement = webDriver.findElement(By.cssSelector("input[type='checkbox']"));
		agreement.click();

		// ログイン前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		// ページ最下部までスクロール
		scrollTo("1000");

		// 更新ボタンを取得して押下する
		final WebElement nextBtn = webDriver.findElement(By.cssSelector("button[type='submit']"));
		nextBtn.click();

		// ログイン前のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// 遷移先のURLの一致確認
		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/password/changePassword", url);

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 パスワードを未入力で「変更」ボタン押下")
	void test04() {

		// ページ最下部までスクロール
		scrollTo("1000");

		// 変更ボタンを取得して押下
		final WebElement submitBtn = webDriver.findElement(By.cssSelector("button[type='submit']"));
		submitBtn.click();

		// 確認モーダルウィンドウ表示前のエビデンス取得
		getEvidence(new Object() {
		}, "beforeModal");

		// 待ち処理
		visibilityTimeout(By.id("upd-btn"), 10);

		// 確認モーダルウィンドウのエビデンス取得
		getEvidence(new Object() {
		}, "modalOpen");

		// 確認モーダルウィンドウの変更ボタンを取得して押下
		final WebElement upBtn = webDriver.findElement(By.id("upd-btn"));
		upBtn.click();

		// 該当箇所までスクロール
		scrollBy("100");

		// 更新後のエビデンス取得
		getEvidence(new Object() {
		}, "afterUpdate");

		// 待ち処理
		visibilityTimeout(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[1]/div/ul/li/span"), 10);

		// エラーメッセージの一致確認(現在のパスワード)
		String currentPwErrorMsg = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[1]/div/ul/li/span")).getText();
		assertEquals("現在のパスワードは必須です。", currentPwErrorMsg);

		// エラーメッセージの一致確認(新しいパスワード)
		String newPwErrorMsg = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[2]/div/ul/li/span")).getText().trim();
		assertTrue("「パスワード」には半角英数字のみ使用可能です。また、半角英大文字、半角英小文字、数字を含めた8～20文字を入力してください。パスワードは必須です。", newPwErrorMsg.contains(newPwErrorMsg));

		// エラーメッセージの一致確認(確認パスワード)
		String pwConfirmErrorMsg = webDriver
				.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[3]/div/ul/li/span")).getText();
		assertEquals("確認パスワードは必須です。", pwConfirmErrorMsg);

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 20文字以上の変更パスワードを入力し「変更」ボタン押下")
	void test05() {

		// 入力欄の取得
		final WebElement currentPassword = webDriver.findElement(By.id("currentPassword"));
		final WebElement password = webDriver.findElement(By.id("password"));
		final WebElement passwordConfirm = webDriver.findElement(By.id("passwordConfirm"));

		// キー入力
		currentPassword.clear();
		currentPassword.sendKeys("StudentAA03");
		password.clear();
		password.sendKeys("studentAA03aaaaaaaaaa");
		passwordConfirm.clear();
		passwordConfirm.sendKeys("studentAA03aaaaaaaaaa");

		// ページ最下部までスクロール
		scrollTo("1000");

		// 変更ボタンを取得して押下
		final WebElement submitBtn = webDriver.findElement(By.cssSelector("button[type='submit']"));
		submitBtn.click();

		// 更新前のエビデンス取得
		getEvidence(new Object() {
		}, "beforeUpdate");

		// 確認モーダルウィンドウの変更ボタンを取得して押下
		final WebElement upBtn = webDriver.findElement(By.id("upd-btn"));
		upBtn.click();

		// 更新後のエビデンス取得
		getEvidence(new Object() {
		}, "afterUpdate");

		// エラーメッセージの一致確認
		String errorMsg = webDriver.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[2]/div/ul/li/span"))
				.getText();
		assertEquals("パスワードの長さが最大値(20)を超えています。", errorMsg);
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 ポリシーに合わない変更パスワードを入力し「変更」ボタン押下")
	void test06() {

		// 入力欄の取得
		final WebElement currentPassword = webDriver.findElement(By.id("currentPassword"));
		final WebElement password = webDriver.findElement(By.id("password"));
		final WebElement passwordConfirm = webDriver.findElement(By.id("passwordConfirm"));

		// キー入力
		currentPassword.clear();
		currentPassword.sendKeys("StudentAA03");
		password.clear();
		password.sendKeys("StudentAA03");
		passwordConfirm.clear();
		passwordConfirm.sendKeys("StudentAA03");

		// ページ最下部までスクロール
		scrollTo("1000");

		// 変更ボタンを取得して押下
		final WebElement submitBtn = webDriver.findElement(By.cssSelector("button[type='submit']"));
		submitBtn.click();

		// 更新前のエビデンス取得
		getEvidence(new Object() {
		}, "beforeUpdate");

		// 確認モーダルウィンドウの変更ボタンを取得して押下
		final WebElement upBtn = webDriver.findElement(By.id("upd-btn"));
		upBtn.click();

		// 更新後のエビデンス取得
		getEvidence(new Object() {
		}, "afterUpdate");

		// エラーメッセージの一致確認
		String errorMsg = webDriver.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[2]/div/ul/li/span"))
				.getText();
		assertEquals("現在と同じパスワードは使用できません。", errorMsg);
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 一致しない確認パスワードを入力し「変更」ボタン押下")
	void test07() {

		// 入力欄の取得
		final WebElement currentPassword = webDriver.findElement(By.id("currentPassword"));
		final WebElement password = webDriver.findElement(By.id("password"));
		final WebElement passwordConfirm = webDriver.findElement(By.id("passwordConfirm"));

		// キー入力
		currentPassword.clear();
		currentPassword.sendKeys("StudentAA03");
		password.clear();
		password.sendKeys("studentAA03");
		passwordConfirm.clear();
		passwordConfirm.sendKeys("studentAAAA");

		// ページ最下部までスクロール
		scrollTo("1000");

		// 変更ボタンを取得して押下
		final WebElement submitBtn = webDriver.findElement(By.cssSelector("button[type='submit']"));
		submitBtn.click();

		// 更新前のエビデンス取得
		getEvidence(new Object() {
		}, "beforeUpdate");

		// 確認モーダルウィンドウの変更ボタンを取得して押下
		final WebElement upBtn = webDriver.findElement(By.id("upd-btn"));
		upBtn.click();

		// 更新後のエビデンス取得
		getEvidence(new Object() {
		}, "afterUpdate");

		// エラーメッセージの一致確認
		String errorMsg = webDriver.findElement(By.xpath("//*[@id=\"upd-form\"]/div[1]/fieldset/div[2]/div/ul/li/span"))
				.getText();
		assertEquals("パスワードと確認パスワードが一致しません。", errorMsg);
	}

}
