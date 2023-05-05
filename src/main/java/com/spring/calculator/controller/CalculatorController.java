package com.spring.calculator.controller;

import com.spring.calculator.model.Number;
import com.spring.calculator.service.NumberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

//Web controller, leidžia naudoti @RequestMapping
/* @RestContoller anotacija nurodo, kad pvz. String tipo rezultatas turėtų būti atvaizduojamas, toks, koks yra
@RestContoller anotacija naudojama tada kai frontedne nenaudojam springo (javascirpt, react, angular)
dažniausiai gražinami formatai, JSON, .xml  t.y. negražinami vaizdo (html, jsp) */
//@RestController

//kadangi mus reikia gražinti (view) pagal spring MVC, naudosime anotacija @Controller
@Controller

/*Žymi konfigūracijos komponentą, viduje leidžia kurti BEAN per metodus su @Bean anotacija
Ši klasės lygio anotacija nurodo Spring   framwork atspėti konfigūraciją
Remiantis priklausomybėmis (JAR bibliotekomis), kurias programuotojas įtraukė į projektą (pom.xml)
Šiuo atveju ji veikia kartu su main method */
@EnableAutoConfiguration
public class CalculatorController {

    /* Kaip perduodami duomenys skirt komponentams:
    vartotojas -> CalculatorController -> NumberServiceImpl -> NumberDAUImpl
     */

    // @Autowired naud automat priklausomybių injekcijai (IOC -iversion of control)
    // kad panaudoti @Autowired anotaciją reikia pirmaiausia turėti apsirašius @Been @Configuration klasė
    @Autowired

    /* @Qualifier anotacija kartu su @ autowired patikslina su kuriuo konkrečiai Been susieti priklausomybę.
 Jeigu @Configuration klasėje yra daugiau nei 1 Been, @Quolifier anotacija yra privaloma, kitų atvėju metama klaida:
  'Consider marking one of the beans as @Primary, updating the consumer to accept multiple beans,
  or using @Qualifier to identify the bean that should be consumed' */
    @Qualifier("NumberService")

    public NumberService numberService;

    //kadangi skaiciuotuvo forma naudoja POST f-ja, cia irgi nurodysime POST
    @PostMapping("/calculate")
    // @RequestParam anotacija perduoda per URL perduodus duomenis (String, String) kurie patalpinami HashMap (K,V)

    public String calculate(
            /* Svarbu: parametras BindingResult turi eiti iškarto po anotacijos @Valid
            Kitu atvėju gausite klaidą "Validation failed for object" */
            @Valid @ModelAttribute("number") Number numb, BindingResult bindingResult,
            @RequestParam HashMap<String, String> inputForm, ModelMap outputForm) {

        /*Per URL perduodamas key, turi pavadinima sk1
        pagal key sk1 ištraukiama reikšmė, pvz. vartotojas įvėdė 8
        mums reikia konvertuoti iš String į int, kad paskaičiuoti rezultatą
        Pirmas String yra key (sk1,sk2, action) o antras  - value
        Key tiek fronted tiek backed turi sutapti
        URL pvz  http://localhost:8080/calc?sk1=20&sk2=20&action=*
        simboliai koduojasi https://meyerweb.com/eric/tools/dencoder/
        jeigu validacijos klaidos(žr. Number class aprašyta validaciją prie kiekvieno skaičiaus) */

        if (bindingResult.hasErrors()) {
            //vartotojas lieka skaičiuotuvo lange tol, kol neištaiso validacijos klaidų
            return "calculator";
        } else { // vartotojas praėjo validaciją
            int sk1 = Integer.parseInt(inputForm.get("sk1"));
            int sk2 = Integer.parseInt(inputForm.get("sk2"));
            String action = inputForm.get("action");
            double result=0 ;
            switch (action) {
                case "*":
                    result =(double) sk1 * sk2;
                    break;
                case "/":
                    if (sk2 != 0) {
                        result = (double) sk1/ sk2;

                    } else {
                        return "error";
                    }
                    break;
                case "-":
                    result = sk1 - sk2;
                    break;
                case "+":
                    result = sk1 + sk2;
                    break;
            }
                //TODO suskaičiuoti ir atspausdinti rezultata, kas iš ko
                // int result = sk1 * sk2 ;
                //inputForm naudojamas siųsi duomenis iš spring MVC controller į JSP failą (vaizdą)
                outputForm.put("sk1", sk1);
                outputForm.put("sk2", sk2);
                outputForm.put("action", action);
                outputForm.put("result", result);

                // Kreipėmes į Service kuris savo ruoštu kreipiasi į DAO ir išsaugoja įrašą DB
                numberService.insert(new Number(sk1,sk2,action,result));

                return "calculate2";
            }
        }

        //grąžinamas vaizdas (forma .jsp)
        //svarbu nurodyti per application.properties prefix ir suffix nes pagal tai ieškos vaizdo projekte
        //return "calculate";
        // ApplicationContext yra interface skirtas suteikti informaciją apie aplikacijos konfigūraciją.
        // Šiuo atveju naudojama konfigūracija paimam iš xml failo
        //  ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");

//        return "Internet calculator <br><br>" +
//                "Galimos operacijos :<br>" +
//                "<ul><li> Sudėti </li>" +
//                "<li> Atimti </li>" +
//                "<li> Dauginti </li>" +
//                "<li> <a href='dalinti'>Dalinti</a> </li></ul>";

        //bean -  kalses objektas, kuris atitinka singleton pattern
        //   HelloWorld bean = (HelloWorld) applicationContext.getBean("helloWorld");
        //  return bean.getHello();

    @GetMapping("/")
    public String homePage(Model model) {
        //jeigu Model 'number' nepraeina validacijos per jį grąžinamos validacijos klaidos į View
        model.addAttribute("number", new Number());
        //grąžiname JSP failą, kuris turi būti talpinamas "webapp -> WEB-INF ->  JSP" folderi
        return "calculator";
    }
    @RequestMapping(method=RequestMethod.GET, value="/numbers")
    public String getAllNumbers(Model model){
        model.addAttribute("numbers",numberService.getAll());
        return "numbers2";

    }

    // id ateina iš frontendo, vartotojui pasirinkus konkretų įrašą
    @RequestMapping(method=RequestMethod.GET, value="/show{id}")
    public String getById(@RequestParam("id") int id,Model model){
        model.addAttribute("number",numberService.getById(id));
        return "number2";
    }

    @RequestMapping(method=RequestMethod.GET, value="/delete{id}")
    public String deleteById(@RequestParam("id") int id,Model model) {
        numberService.delete(id);
        model.addAttribute("numbers", numberService.getAll());
        return "numbers2";
    }
        /*
        Atnaujinant įrašą pirmiasia reikia jį parodyti
         */
        @RequestMapping(method=RequestMethod.GET, value="/update{id}")
        public String updateById(@RequestParam("id") int id,Model model){
            /*Kai užkrauname įrašo redagavimo forma, privalome jos laukelius užpildyti įrašo informacija
             */
            model.addAttribute("number",numberService.getById(id));
            return "update";
        }

        //Kadangi forma naudoja POST, čia taip pat naudojame POST
        @RequestMapping(method=RequestMethod.POST, value="/updateNumber")
        public String updateNumber(@ModelAttribute("number")Number number){
            numberService.update(number);
            // redirect nukreipia vartotoją į įrašo atvaizdavimo puslapį(getById)
            return "redirect:/show?id="+number.getId();
        }

    }

