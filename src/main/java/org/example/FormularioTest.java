package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class FormularioTest {
    private final String URL = "https://igorsmasc.github.io/fomulario_cadastro_selenium/";
    private WebDriver driver;

    @BeforeEach
    void beforeEach() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(URL);
    }

    @AfterEach
    public void afterEach(){
        driver.quit();
    }

    @Test
    void radioButtonSexoTest(){
        WebElement masculino = driver.findElement(By.id("masculino"));
        WebElement feminino = driver.findElement(By.id("feminino"));
        WebElement outro = driver.findElement(By.id("outro"));

        elementClick(masculino);
        Assertions.assertTrue(masculino.isSelected());
        Assertions.assertFalse(feminino.isSelected());
        Assertions.assertFalse(outro.isSelected());

        elementClick(feminino);
        Assertions.assertTrue(feminino.isSelected());
        Assertions.assertFalse(masculino.isSelected());
        Assertions.assertFalse(outro.isSelected());

        elementClick(outro);
        Assertions.assertTrue(outro.isSelected());
        Assertions.assertFalse(masculino.isSelected());
        Assertions.assertFalse(feminino.isSelected());

    }

    @Test
    void checkBoxConhecimentoTest(){
        WebElement java = driver.findElement(By.id("java"));
        WebElement selenium = driver.findElement(By.id("selenium"));
        WebElement react = driver.findElement(By.id("react"));
        WebElement junit = driver.findElement(By.id("junit"));
        WebElement javascript = driver.findElement(By.id("javascript"));

        java.click();
        selenium.click();
        junit.click();

        Assertions.assertTrue(java.isSelected());
        Assertions.assertTrue(selenium.isSelected());
        Assertions.assertTrue(junit.isSelected());
        Assertions.assertFalse(react.isSelected());
        Assertions.assertFalse(javascript.isSelected());
    }

    @Test
    void selectSimplesMotivacaoTest(){
        WebElement selectWebElement = driver.findElement(By.id("motivacao"));
        Select select = new Select(selectWebElement);

        select.selectByVisibleText("Testes");
        Assertions.assertEquals("Testes",select.getFirstSelectedOption().getText());
    }

    @Test
    void selectMultiploAreaInteresseTest(){
        WebElement selectWebElement = driver.findElement(By.id("area-interesse"));
        Select select = new Select(selectWebElement);

        select.selectByVisibleText("Frontend");
        select.selectByValue("Devops");

        List<WebElement> elementosSelecionados = select.getAllSelectedOptions();
        List<String> elementosSelecionadosString = elementosSelecionados.stream().map(elemento -> elemento.getText()).collect(Collectors.toList());

        Assertions.assertArrayEquals(new String[]{"Frontend", "DevOps"}, elementosSelecionadosString.toArray());
    }

    @Test
    void botaoMaisInformacoesTest(){
        WebElement element = driver.findElement(By.id("btn-info"));
        element.click();

        Assertions.assertTrue(existeAlertaAberto());
        Alert alert = driver.switchTo().alert();
        Assertions.assertEquals("Este formulário é para cadastro de candidatos interessados em vagas de emprego. Por favor, preencha todos os campos obrigatórios e forneça informações precisas e atualizadas. Obrigado!",alert.getText());
        alert.accept();
        Assertions.assertFalse(existeAlertaAberto());
    }

    @Test
    void abaSobreEmpresaTest(){
        driver.findElement(By.linkText("Sobre a empresa")).click();
        driver.switchTo().window(((String) driver.getWindowHandles().toArray()[1]));

        WebElement element = driver.findElement(By.tagName("h1"));
        Assertions.assertEquals("A melhor empresa do mundo",element.getText());
    }

    @Test
    void cadastroComTodosCamposTest(){
        WebElement campoNome = driver.findElement(By.id("nome"));
        campoNome.sendKeys("Geralt");

        WebElement campoSobrenome = driver.findElement(By.id("sobrenome"));
        campoSobrenome.sendKeys("de Rivia");

        WebElement campoSexo = driver.findElement(By.id("masculino"));
        campoSexo.click();

        WebElement  checkJava = driver.findElement(By.id("java"));
        checkJava.click();
        WebElement checkJUnit = driver.findElement(By.id("junit"));
        checkJUnit.click();

        WebElement selectAreaInteresseWeb = driver.findElement(By.id("area-interesse"));
        Select selectAreaInteresse = new Select(selectAreaInteresseWeb);
        selectAreaInteresse.selectByVisibleText("Backend");
        selectAreaInteresse.selectByVisibleText("Testes");

        WebElement selectMotivacaoWeb = driver.findElement(By.id("motivacao"));
        Select selectMotivacao = new Select(selectMotivacaoWeb);
        selectMotivacao.selectByVisibleText("Backend");

        WebElement elementTextArea = driver.findElement(By.id("porque"));
        elementTextArea.sendKeys("Trabalho de bruxo");

        WebElement elementForm = driver.findElement(By.id("formulario"));
        elementForm.submit();

        Alert alert = driver.switchTo().alert();
        Assertions.assertEquals("Você preencheu tudo corretamente e é sua última resposta?",alert.getText());
        alert.accept();

        WebElement nomeRegistro = driver.findElement(By.cssSelector("#table-cadastros-body > tr > td:nth-child(1)"));
        WebElement sobrenomeRegistro = driver.findElement(By.xpath("//*[@id=\"table-cadastros-body\"]/tr/td[2]"));
        WebElement sexoRegistro = driver.findElement(By.cssSelector("#table-cadastros-body > tr > td:nth-child(3)"));
        Assertions.assertEquals("Geralt",nomeRegistro.getText());
        Assertions.assertEquals("de Rivia",sobrenomeRegistro.getText());
        Assertions.assertEquals("masculino",sexoRegistro.getText());
    }

    @Test
    void cadastroSemCampoNomeTest(){
//        WebElement campoNome = driver.findElement(By.id("nome"));
//        campoNome.sendKeys("Geralt");

        WebElement campoSobrenome = driver.findElement(By.id("sobrenome"));
        campoSobrenome.sendKeys("de Rivia");

        WebElement campoSexo = driver.findElement(By.id("masculino"));
        campoSexo.click();

        WebElement  checkJava = driver.findElement(By.id("java"));
        checkJava.click();
        WebElement checkJUnit = driver.findElement(By.id("junit"));
        checkJUnit.click();

        WebElement selectAreaInteresseWeb = driver.findElement(By.id("area-interesse"));
        Select selectAreaInteresse = new Select(selectAreaInteresseWeb);
        selectAreaInteresse.selectByVisibleText("Backend");
        selectAreaInteresse.selectByVisibleText("Testes");

        WebElement selectMotivacaoWeb = driver.findElement(By.id("motivacao"));
        Select selectMotivacao = new Select(selectMotivacaoWeb);
        selectMotivacao.selectByVisibleText("Backend");

        WebElement elementTextArea = driver.findElement(By.id("porque"));
        elementTextArea.sendKeys("Trabalho de bruxo");

        WebElement elementForm = driver.findElement(By.id("formulario"));
        elementForm.submit();

        Alert alert = driver.switchTo().alert();
        Assertions.assertEquals("Por favor, preencha todos os campos.",alert.getText());
        alert.accept();
    }

    @Test
    void cadastroSemCampoSobrenomeTest(){
        WebElement campoNome = driver.findElement(By.id("nome"));
        campoNome.sendKeys("Geralt");

//        WebElement campoSobrenome = driver.findElement(By.id("sobrenome"));
//        campoSobrenome.sendKeys("de Rivia");

        WebElement campoSexo = driver.findElement(By.id("masculino"));
        campoSexo.click();

        WebElement  checkJava = driver.findElement(By.id("java"));
        checkJava.click();
        WebElement checkJUnit = driver.findElement(By.id("junit"));
        checkJUnit.click();

        WebElement selectAreaInteresseWeb = driver.findElement(By.id("area-interesse"));
        Select selectAreaInteresse = new Select(selectAreaInteresseWeb);
        selectAreaInteresse.selectByVisibleText("Backend");
        selectAreaInteresse.selectByVisibleText("Testes");

        WebElement selectMotivacaoWeb = driver.findElement(By.id("motivacao"));
        Select selectMotivacao = new Select(selectMotivacaoWeb);
        selectMotivacao.selectByVisibleText("Backend");

        WebElement elementTextArea = driver.findElement(By.id("porque"));
        elementTextArea.sendKeys("Trabalho de bruxo");

        WebElement elementForm = driver.findElement(By.id("formulario"));
        elementForm.submit();

        Alert alert = driver.switchTo().alert();
        Assertions.assertEquals("Por favor, preencha todos os campos.",alert.getText());
        alert.accept();
    }

    @Test
    void cadastroSemCampoSexoTest(){
        WebElement campoNome = driver.findElement(By.id("nome"));
        campoNome.sendKeys("Geralt");

        WebElement campoSobrenome = driver.findElement(By.id("sobrenome"));
        campoSobrenome.sendKeys("de Rivia");

//        WebElement campoSexo = driver.findElement(By.id("masculino"));
//        campoSexo.click();

        WebElement  checkJava = driver.findElement(By.id("java"));
        checkJava.click();
        WebElement checkJUnit = driver.findElement(By.id("junit"));
        checkJUnit.click();

        WebElement selectAreaInteresseWeb = driver.findElement(By.id("area-interesse"));
        Select selectAreaInteresse = new Select(selectAreaInteresseWeb);
        selectAreaInteresse.selectByVisibleText("Backend");
        selectAreaInteresse.selectByVisibleText("Testes");

        WebElement selectMotivacaoWeb = driver.findElement(By.id("motivacao"));
        Select selectMotivacao = new Select(selectMotivacaoWeb);
        selectMotivacao.selectByVisibleText("Backend");

        WebElement elementTextArea = driver.findElement(By.id("porque"));
        elementTextArea.sendKeys("Trabalho de bruxo");

        WebElement elementForm = driver.findElement(By.id("formulario"));
        elementForm.submit();

        Alert alert = driver.switchTo().alert();
        Assertions.assertEquals("Por favor, preencha todos os campos.",alert.getText());
        alert.accept();
    }

    @Test
    void cadastroSemCampoAreaInteresseTest(){
        WebElement campoNome = driver.findElement(By.id("nome"));
        campoNome.sendKeys("Geralt");

        WebElement campoSobrenome = driver.findElement(By.id("sobrenome"));
        campoSobrenome.sendKeys("de Rivia");

        WebElement campoSexo = driver.findElement(By.id("masculino"));
        campoSexo.click();

        WebElement  checkJava = driver.findElement(By.id("java"));
        checkJava.click();
        WebElement checkJUnit = driver.findElement(By.id("junit"));
        checkJUnit.click();

//        WebElement selectAreaInteresseWeb = driver.findElement(By.id("area-interesse"));
//        Select selectAreaInteresse = new Select(selectAreaInteresseWeb);
//        selectAreaInteresse.selectByVisibleText("Backend");
//        selectAreaInteresse.selectByVisibleText("Testes");

        WebElement selectMotivacaoWeb = driver.findElement(By.id("motivacao"));
        Select selectMotivacao = new Select(selectMotivacaoWeb);
        selectMotivacao.selectByVisibleText("Backend");

        WebElement elementTextArea = driver.findElement(By.id("porque"));
        elementTextArea.sendKeys("Trabalho de bruxo");

        WebElement elementForm = driver.findElement(By.id("formulario"));
        elementForm.submit();

        Alert alert = driver.switchTo().alert();
        Assertions.assertEquals("Por favor, preencha todos os campos.",alert.getText());
        alert.accept();
    }

    @Test
    void cadastroSemCampoAreaPrincipalTest(){
        WebElement campoNome = driver.findElement(By.id("nome"));
        campoNome.sendKeys("Geralt");

        WebElement campoSobrenome = driver.findElement(By.id("sobrenome"));
        campoSobrenome.sendKeys("de Rivia");

        WebElement campoSexo = driver.findElement(By.id("masculino"));
        campoSexo.click();

        WebElement  checkJava = driver.findElement(By.id("java"));
        checkJava.click();
        WebElement checkJUnit = driver.findElement(By.id("junit"));
        checkJUnit.click();

        WebElement selectAreaInteresseWeb = driver.findElement(By.id("area-interesse"));
        Select selectAreaInteresse = new Select(selectAreaInteresseWeb);
        selectAreaInteresse.selectByVisibleText("Backend");
        selectAreaInteresse.selectByVisibleText("Testes");

//        WebElement selectMotivacaoWeb = driver.findElement(By.id("motivacao"));
//        Select selectMotivacao = new Select(selectMotivacaoWeb);
//        selectMotivacao.selectByVisibleText("Backend");

        WebElement elementTextArea = driver.findElement(By.id("porque"));
        elementTextArea.sendKeys("Trabalho de bruxo");

        WebElement elementForm = driver.findElement(By.id("formulario"));
        elementForm.submit();

        Alert alert = driver.switchTo().alert();
        Assertions.assertEquals("Por favor, preencha todos os campos.",alert.getText());
        alert.accept();
    }

    @Test
    void cadastroSemCampoConhecimentoTest(){
        WebElement campoNome = driver.findElement(By.id("nome"));
        campoNome.sendKeys("Geralt");

        WebElement campoSobrenome = driver.findElement(By.id("sobrenome"));
        campoSobrenome.sendKeys("de Rivia");

        WebElement campoSexo = driver.findElement(By.id("masculino"));
        campoSexo.click();

//        WebElement  checkJava = driver.findElement(By.id("java"));
//        checkJava.click();
//        WebElement checkJUnit = driver.findElement(By.id("junit"));
//        checkJUnit.click();

        WebElement selectAreaInteresseWeb = driver.findElement(By.id("area-interesse"));
        Select selectAreaInteresse = new Select(selectAreaInteresseWeb);
        selectAreaInteresse.selectByVisibleText("Backend");
        selectAreaInteresse.selectByVisibleText("Testes");

        WebElement selectMotivacaoWeb = driver.findElement(By.id("motivacao"));
        Select selectMotivacao = new Select(selectMotivacaoWeb);
        selectMotivacao.selectByVisibleText("Backend");

        WebElement elementTextArea = driver.findElement(By.id("porque"));
        elementTextArea.sendKeys("Trabalho de bruxo");

        WebElement elementForm = driver.findElement(By.id("formulario"));
        elementForm.submit();

        Alert alert = driver.switchTo().alert();
        Assertions.assertEquals("Você preencheu tudo corretamente e é sua última resposta?",alert.getText());
        alert.accept();

        WebElement nomeRegistro = driver.findElement(By.cssSelector("#table-cadastros-body > tr > td:nth-child(1)"));
        Assertions.assertEquals("Geralt",nomeRegistro.getText());
    }

    @Test
    void cadastroSemCampoTextAreaTest(){
        WebElement campoNome = driver.findElement(By.id("nome"));
        campoNome.sendKeys("Geralt");

        WebElement campoSobrenome = driver.findElement(By.id("sobrenome"));
        campoSobrenome.sendKeys("de Rivia");

        WebElement campoSexo = driver.findElement(By.id("masculino"));
        campoSexo.click();

        WebElement  checkJava = driver.findElement(By.id("java"));
        checkJava.click();
        WebElement checkJUnit = driver.findElement(By.id("junit"));
        checkJUnit.click();

        WebElement selectAreaInteresseWeb = driver.findElement(By.id("area-interesse"));
        Select selectAreaInteresse = new Select(selectAreaInteresseWeb);
        selectAreaInteresse.selectByVisibleText("Backend");
        selectAreaInteresse.selectByVisibleText("Testes");

        WebElement selectMotivacaoWeb = driver.findElement(By.id("motivacao"));
        Select selectMotivacao = new Select(selectMotivacaoWeb);
        selectMotivacao.selectByVisibleText("Backend");

//        WebElement elementTextArea = driver.findElement(By.id("porque"));
//        elementTextArea.sendKeys("Trabalho de bruxo");

        WebElement elementForm = driver.findElement(By.id("formulario"));
        elementForm.submit();

        Alert alert = driver.switchTo().alert();
        Assertions.assertEquals("Você preencheu tudo corretamente e é sua última resposta?",alert.getText());
        alert.accept();

        WebElement nomeRegistro = driver.findElement(By.cssSelector("#table-cadastros-body > tr > td:nth-child(1)"));
        Assertions.assertEquals("Geralt",nomeRegistro.getText());
    }

    @Test
    void cadastroCampoNomeSomenteNumerosTest(){
        WebElement campoNome = driver.findElement(By.id("nome"));
        campoNome.sendKeys("123456");

        WebElement campoSobrenome = driver.findElement(By.id("sobrenome"));
        campoSobrenome.sendKeys("de Rivia");

        WebElement campoSexo = driver.findElement(By.id("masculino"));
        campoSexo.click();

        WebElement  checkJava = driver.findElement(By.id("java"));
        checkJava.click();
        WebElement checkJUnit = driver.findElement(By.id("junit"));
        checkJUnit.click();

        WebElement selectAreaInteresseWeb = driver.findElement(By.id("area-interesse"));
        Select selectAreaInteresse = new Select(selectAreaInteresseWeb);
        selectAreaInteresse.selectByVisibleText("Backend");
        selectAreaInteresse.selectByVisibleText("Testes");

        WebElement selectMotivacaoWeb = driver.findElement(By.id("motivacao"));
        Select selectMotivacao = new Select(selectMotivacaoWeb);
        selectMotivacao.selectByVisibleText("Backend");

        WebElement elementTextArea = driver.findElement(By.id("porque"));
        elementTextArea.sendKeys("Trabalho de bruxo");

        WebElement elementForm = driver.findElement(By.id("formulario"));
        elementForm.submit();

        Alert alert = driver.switchTo().alert();

        String msgAlert = "Você preencheu tudo corretamente e é sua última resposta?";

        if(msgAlert.equals(alert.getText())){
            Assertions.fail("Não pode permitir cadastrar NUMBER como NOME");
        }
    }

    @Test
    void cadastroCampoNomeEspacoVazioTest(){
        WebElement campoNome = driver.findElement(By.id("nome"));
        campoNome.sendKeys(" ");

        WebElement campoSobrenome = driver.findElement(By.id("sobrenome"));
        campoSobrenome.sendKeys("de Rivia");

        WebElement campoSexo = driver.findElement(By.id("masculino"));
        campoSexo.click();

        WebElement  checkJava = driver.findElement(By.id("java"));
        checkJava.click();
        WebElement checkJUnit = driver.findElement(By.id("junit"));
        checkJUnit.click();

        WebElement selectAreaInteresseWeb = driver.findElement(By.id("area-interesse"));
        Select selectAreaInteresse = new Select(selectAreaInteresseWeb);
        selectAreaInteresse.selectByVisibleText("Backend");
        selectAreaInteresse.selectByVisibleText("Testes");

        WebElement selectMotivacaoWeb = driver.findElement(By.id("motivacao"));
        Select selectMotivacao = new Select(selectMotivacaoWeb);
        selectMotivacao.selectByVisibleText("Backend");

        WebElement elementTextArea = driver.findElement(By.id("porque"));
        elementTextArea.sendKeys("Trabalho de bruxo");

        WebElement elementForm = driver.findElement(By.id("formulario"));
        elementForm.submit();

        Alert alert = driver.switchTo().alert();

        String msgAlert = "Você preencheu tudo corretamente e é sua última resposta?";

        if(msgAlert.equals(alert.getText())){
            Assertions.fail("Não pode permitir cadastrar SOMENTE ESPAÇO VAZIO");
        }
    }

    private void elementClick(WebElement element){
        //((JavascriptExecutor)driver).executeScript("arguments[0].click()", element);
        element.click();
    }

    private boolean existeAlertaAberto(){
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e){
            return false;
        }
    }
}
