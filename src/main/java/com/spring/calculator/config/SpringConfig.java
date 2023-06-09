package com.spring.calculator.config;

import com.spring.calculator.model.NumberDAO;
import com.spring.calculator.model.NumberDAOImpl;
import com.spring.calculator.service.NumberService;
import com.spring.calculator.service.NumberServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*Configuration anotacija žyminti konfiguracijos komponentą
Viduje leidžianti kurti Bean per metodus su @Bean anotacija
*/
@Configuration
public class SpringConfig {
    /*
    Bean tai objektai kurie sudaro Spring aplikacijos pagrindą.
    Paprastai tai Java klasė realizuojanti tam tikrą interfeisą ir Java @Been specifikacija.
    @Bean- atitinka SINGLETON šabloną- programinės įrangos projektavimo schema,
    kuri riboja klasės įvykdimą vienu vieninteliu egzemplioriu.
    Tai naudinga kai reikia tiksliai vieno objekto, norint koordinuoti veiksmus visoje sistemoje.
     */
    @Bean
    /*
    @Qualifier anotacija kartu su @Autowired patikslina su kuriuo konkrečiai Been susieti priklausomybę.
    Jeigu @Configuration klasėje yra daugiau nei 1 Been, @Quolifier anotacija yra privaloma, kitų atvėju metama klaida:
     'Consider marking one of the beans as @Primary, updating the consumer to accept multiple beans,
     or using @Qualifier to identify the bean
      that should be consumed'
     */
    @Qualifier("NumberDAO")
    public NumberDAO getNumberDAO() {
        return new NumberDAOImpl();
    }

    @Bean
    @Qualifier("NumberService")
    public NumberService getNumberService(){
        return new NumberServiceImpl();
    }

}
