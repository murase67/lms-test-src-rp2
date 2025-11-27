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
 * ケース17
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース17 受講生 初回ログイン 正常系")
public class Case17 {

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
		loginId.sendKeys("StudentAA05");
		password.clear();
		password.sendKeys("StudentAA05");

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

		// 遷移前のエビデンス取得
		getEvidence(new Object() {
		}, "before");

		// ページ最下部までスクロール
		scrollTo("1000");

		// 更新ボタンを取得して押下する
		final WebElement nextBtn = webDriver.findElement(By.cssSelector("button[type='submit']"));
		nextBtn.click();

		// 遷移後のエビデンス取得
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

		// 入力欄の取得
		final WebElement currentPassword = webDriver.findElement(By.id("currentPassword"));
		final WebElement password = webDriver.findElement(By.id("password"));
		final WebElement passwordConfirm = webDriver.findElement(By.id("passwordConfirm"));

		// キー入力
		currentPassword.clear();
		currentPassword.sendKeys("StudentAA05");
		password.clear();
		password.sendKeys("studentAA05");
		passwordConfirm.clear();
		passwordConfirm.sendKeys("studentAA05");

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

		pageLoadTimeout(10);

		// 遷移後のエビデンス取得
		getEvidence(new Object() {
		}, "after");

		// 遷移先のURLの一致確認
		String url = webDriver.getCurrentUrl();
		assertEquals("http://localhost:8080/lms/course/detail", url);
	}

}
