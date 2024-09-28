package techproed.utilities;

import org.testng.*;
import org.testng.annotations.ITestAnnotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Listeners implements ITestListener, IRetryAnalyzer, IAnnotationTransformer {

    @Override
    public void onStart(ITestContext context) {
        System.out.println("onStart ==> Tüm testlerden önce çağrılır: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("onFinish ==> Tüm testlerden sonra çağrılır: " + context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("onTestStart ==> Her bir @Test'ten önce çağrılır: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("onTestSuccess ==> Başarılı olan testten sonra çağrılır: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("onTestFailure ==> Başarısız olan testten sonra çağrılır: " + result.getName());
        ReusableMethods.screenShot(result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("onTestSkipped ==> Atlama yapılan testten sonra çağrılır: " + result.getName());
    }



    // Her test için deneme (retry) sayısını takip eden bir Map. Test metodu ismiyle sayıyı eşleştiriyoruz.
    private static Map<String, Integer> retryCounts = new HashMap<>();

    // Test yeniden çalıştırma (retry) işlemi için kullanılır. Eğer test başarısız olursa, burada belirtilen sayı kadar yeniden çalıştırılır.
    private static final int maxRetryCount = 1;

    @Override
    public boolean retry(ITestResult result) {
        String testMethodName = result.getMethod().getMethodName();  // Test metodu ismini alır.
        // Eğer bu metod için daha önce bir deneme yapılmamışsa, 0 olarak başlatır.
        retryCounts.putIfAbsent(testMethodName, 0);
        int retryCount = retryCounts.get(testMethodName);  // Şu anki deneme sayısını alır.
        // Eğer deneme sayısı maksimumdan küçükse, yeniden çalıştırır.
        if (retryCount < maxRetryCount) {
            retryCount++;  // Deneme sayısını artırır.
            retryCounts.put(testMethodName, retryCount);  // Günceller.
            return true;  // Testi yeniden çalıştır.
        }
        return false;  // Test yeniden çalıştırılmayacak.
    }

    // Bu metod, TestNG'nin her test metodu için retry mekanizmasını eklemesi için kullanılır.
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        // Her test metoduna retry analyzer ekler. Bu sayede test başarısız olursa belirlenen sayıda yeniden çalıştırılır.
        annotation.setRetryAnalyzer(Listeners.class);
    }
}