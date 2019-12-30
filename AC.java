/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airconditioncontrol;

import com.fuzzylite.*;
import com.fuzzylite.activation.General;
import com.fuzzylite.defuzzifier.Centroid;
import com.fuzzylite.norm.s.Maximum;
import com.fuzzylite.norm.t.Minimum;
import com.fuzzylite.rule.Rule;
import com.fuzzylite.rule.RuleBlock;
import com.fuzzylite.term.Ramp;
import com.fuzzylite.variable.InputVariable;
import com.fuzzylite.variable.OutputVariable;
import java.util.Scanner;

/**
 *
 * @author ATIK
 */
public class AirConditionControl {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         Engine engine = new Engine();
         Scanner sc = new Scanner(System.in);
         
          
         System.out.println("Please enter information");
         System.out.print("Temperature:");
         String temperature = sc.nextLine();
         System.out.print("Humidity:");
          String humidity = sc.nextLine();
          System.out.print("Oxygen:");
          String oxygen = sc.nextLine();
          System.out.print("Particle:");
          String particle = sc.nextLine();
                
                
         if (temperature.isEmpty()||humidity.isEmpty()||oxygen.isEmpty()||particle.isEmpty() ){
                    System.out.println("Please enter information");
                }else {
                    setEngineAndGetFuzzyResult(Double.parseDouble(temperature),
                            Double.parseDouble(humidity),
                            Double.parseDouble(oxygen),
                            Double.parseDouble(particle));
                }
    }
    
    public static void setEngineAndGetFuzzyResult(double temperature,double  humidity,double oxygen,double particle){
        Engine engine = new Engine();
        engine.setName("Air Condition Control System");
        engine.setDescription("It will give the state of air condition");

        InputVariable temperatureInput  = new InputVariable();
        temperatureInput.setName("p");
        temperatureInput.setDescription("");
        temperatureInput.setEnabled(true);
        temperatureInput.setRange(16.000, 28.000); 
        temperatureInput.setLockValueInRange(true);
        temperatureInput.addTerm(new Ramp("LOW", 28.000, 16.000));
        temperatureInput.addTerm(new Ramp("MEDIUM", 18.000, 24.000));
        temperatureInput.addTerm(new Ramp("HIGH", 21.000, 28.000));
        engine.addInputVariable(temperatureInput);



        InputVariable humidityInput   = new InputVariable();
        humidityInput.setName("q");
        humidityInput.setDescription("");
        humidityInput.setEnabled(true);
        humidityInput.setRange(12.000, 60.000);
        humidityInput.setLockValueInRange(true);
        humidityInput.addTerm(new Ramp("LOW", 25.000, 12.000));
        humidityInput.addTerm(new Ramp("MEDIUM", 15.000, 33.000));
        humidityInput.addTerm(new Ramp("HIGH", 24.000, 60.000));
        engine.addInputVariable(humidityInput);


        InputVariable oxygenInput   = new InputVariable();
        oxygenInput.setName("r");
        oxygenInput.setDescription("");
        oxygenInput.setEnabled(true);
        oxygenInput.setRange(10.000, 45.000);
        oxygenInput.setLockValueInRange(true);
        oxygenInput.addTerm(new Ramp("LOW", 21.000, 10.000));
        oxygenInput.addTerm(new Ramp("MEDIUM", 17.000, 25.000));
        oxygenInput.addTerm(new Ramp("HIGH", 21.000, 45.000));
        engine.addInputVariable(oxygenInput);

        InputVariable particleInput   = new InputVariable();
        particleInput.setName("s");
        particleInput.setDescription("");
        particleInput.setEnabled(true);
        particleInput.setRange(1.000, 2000.000);
        particleInput.setLockValueInRange(true);
        particleInput.addTerm(new Ramp("LOW", 330.000, 1.000));
        particleInput.addTerm(new Ramp("MEDIUM", 280.000, 380.000));
        particleInput.addTerm(new Ramp("HIGH", 330.000, 2000.000));
        engine.addInputVariable(particleInput);

        OutputVariable output = new OutputVariable();
        output.setName("t");
        output.setDescription("");
        output.setEnabled(true);
        output.setRange(800.000, 1600.000);
        output.setLockValueInRange(true);
        output.setAggregation(new Maximum());
        output.setDefuzzifier(new Centroid(100));
        output.setDefaultValue(Double.NaN);
        output.setLockPreviousValue(false);
        output.addTerm(new Ramp("LOW", 1200.000, 800.000));
        output.addTerm(new Ramp("MEDIUM", 1000.000, 1400.600));
        output.addTerm(new Ramp("HIGH", 1200.500, 1600.000));
        engine.addOutputVariable(output);

        RuleBlock mamdani = new RuleBlock();
        mamdani.setName("mamdani");
        mamdani.setDescription("");
        mamdani.setEnabled(true);
        mamdani.setConjunction(new Minimum());
        mamdani.setDisjunction(new Maximum());
        mamdani.setImplication(new Minimum());
        mamdani.setActivation(new General());
       mamdani.addRule(Rule.parse("if p is LOW and q is LOW and r is LOW and s is LOW then t is HIGH", engine));//1HL
        
       /*
       
       mamdani.addRule(Rule.parse("if p is Y and q is L and r is L and s is H then t is HL", engine));//2
        mamdani.addRule(Rule.parse("if p is Y and q is L and r is MD and s is L then t is HL", engine));//3HL
        mamdani.addRule(Rule.parse("if p is Y and q is L and r is MD and s is H then t is MD", engine));//4
        mamdani.addRule(Rule.parse("if p is Y and q is L and r is H and s is L then t is HL", engine));//5HL
        mamdani.addRule(Rule.parse("if p is Y and q is L and r is H and s is H then t is MD", engine));//6
        mamdani.addRule(Rule.parse("if p is Y and q is MD and r is L and s is L then t is HL", engine));//7HL
        mamdani.addRule(Rule.parse("if p is Y and q is MD and r is L and s is H then t is MD", engine));//8
        mamdani.addRule(Rule.parse("if p is Y and q is MD and r is MD and s is L then t is MD", engine));//9
        mamdani.addRule(Rule.parse("if p is Y and q is MD and r is MD and s is H then t is SC", engine));//10
        mamdani.addRule(Rule.parse("if p is Y and q is MD and r is H and s is L then t is MD", engine));//11
        mamdani.addRule(Rule.parse("if p is Y and q is MD and r is H and s is H then t is SC", engine));//12
        mamdani.addRule(Rule.parse("if p is Y and q is H and r is L and s is L then t is HL", engine));//13HL
        mamdani.addRule(Rule.parse("if p is Y and q is H and r is L and s is H then t is MD", engine));//14
        mamdani.addRule(Rule.parse("if p is Y and q is H and r is MD and s is L then t is HL", engine));//15HL
        mamdani.addRule(Rule.parse("if p is Y and q is H and r is MD and s is H then t is MD", engine));//16
        mamdani.addRule(Rule.parse("if p is Y and q is H and r is H and s is L then t is MD", engine));//17
        mamdani.addRule(Rule.parse("if p is Y and q is H and r is H and s is H then t is SC", engine));//18
        mamdani.addRule(Rule.parse("if p is Y and q is VH and r is L and s is L then t is HL", engine));//19HL
        mamdani.addRule(Rule.parse("if p is Y and q is VH and r is L and s is H then t is MD", engine));//20
        mamdani.addRule(Rule.parse("if p is Y and q is VH and r is MD and s is L then t is HL", engine));//21HL
        mamdani.addRule(Rule.parse("if p is Y and q is VH and r is MD and s is H then t is MD", engine));//22
        mamdani.addRule(Rule.parse("if p is Y and q is VH and r is H and s is L then t is MD", engine));//23
        mamdani.addRule(Rule.parse("if p is Y and q is VH and r is H and s is H then t is SC", engine));//24
        
        mamdani.addRule(Rule.parse("if p is MI and q is L and r is L and s is L then t is HL", engine));//25
        mamdani.addRule(Rule.parse("if p is MI and q is L and r is L and s is H then t is HL", engine));//26
        mamdani.addRule(Rule.parse("if p is MI and q is L and r is MD and s is L then t is HL", engine));//27
        mamdani.addRule(Rule.parse("if p is MI and q is L and r is MD and s is H then t is MD", engine));//28
        mamdani.addRule(Rule.parse("if p is MI and q is L and r is H and s is L then t is HL", engine));//29
        mamdani.addRule(Rule.parse("if p is MI and q is L and r is H and s is H then t is MD", engine));//30
        mamdani.addRule(Rule.parse("if p is MI and q is MD and r is L and s is L then t is HL", engine));//31
        mamdani.addRule(Rule.parse("if p is MI and q is MD and r is L and s is H then t is MD", engine));//32
        mamdani.addRule(Rule.parse("if p is MI and q is MD and r is MD and s is L then t is MD", engine));//33
        mamdani.addRule(Rule.parse("if p is MI and q is MD and r is MD and s is H then t is SC", engine));//34
        mamdani.addRule(Rule.parse("if p is MI and q is MD and r is H and s is L then t is MD", engine));//35
        mamdani.addRule(Rule.parse("if p is MI and q is MD and r is H and s is H then t is SC", engine));//36
        mamdani.addRule(Rule.parse("if p is MI and q is H and r is L and s is L then t is HL", engine));//37
        mamdani.addRule(Rule.parse("if p is MI and q is H and r is L and s is H then t is MD", engine));//38
        mamdani.addRule(Rule.parse("if p is MI and q is H and r is MD and s is L then t is MD", engine));//39
        mamdani.addRule(Rule.parse("if p is MI and q is H and r is MD and s is H then t is SC", engine));//40
        mamdani.addRule(Rule.parse("if p is MI and q is H and r is H and s is L then t is MD", engine));//41
        mamdani.addRule(Rule.parse("if p is MI and q is H and r is H and s is H then t is SC", engine));//42
        mamdani.addRule(Rule.parse("if p is MI and q is VH and r is L and s is L then t is HL", engine));//43
        mamdani.addRule(Rule.parse("if p is MI and q is VH and r is L and s is H then t is MD", engine));//44
        mamdani.addRule(Rule.parse("if p is MI and q is VH and r is MD and s is L then t is MD", engine));//45
        mamdani.addRule(Rule.parse("if p is MI and q is VH and r is MD and s is H then t is SC", engine));//46
        mamdani.addRule(Rule.parse("if p is MI and q is VH and r is H and s is L then t is MD", engine));//47
        mamdani.addRule(Rule.parse("if p is MI and q is VH and r is H and s is H then t is SC", engine));//48
        
        mamdani.addRule(Rule.parse("if p is O and q is L and r is L and s is L then t is HL", engine));//49
        mamdani.addRule(Rule.parse("if p is O and q is L and r is L and s is H then t is MD", engine));//50
        mamdani.addRule(Rule.parse("if p is O and q is L and r is MD and s is L then t is HL", engine));//51
        mamdani.addRule(Rule.parse("if p is O and q is L and r is MD and s is H then t is MD", engine));//52
        mamdani.addRule(Rule.parse("if p is O and q is L and r is H and s is L then t is HL", engine));//53
        mamdani.addRule(Rule.parse("if p is O and q is L and r is H and s is H then t is MD", engine));//54
        mamdani.addRule(Rule.parse("if p is O and q is MD and r is L and s is L then t is MD", engine));//55
        mamdani.addRule(Rule.parse("if p is O and q is MD and r is L and s is H then t is MD", engine));//56
        mamdani.addRule(Rule.parse("if p is O and q is MD and r is MD and s is L then t is MD", engine));//57
        mamdani.addRule(Rule.parse("if p is O and q is MD and r is MD and s is H then t is SC", engine));//58
        mamdani.addRule(Rule.parse("if p is O and q is MD and r is H and s is L then t is MD", engine));//59
        mamdani.addRule(Rule.parse("if p is O and q is MD and r is H and s is H then t is SC", engine));//60
        mamdani.addRule(Rule.parse("if p is O and q is H and r is L and s is L then t is HL", engine));//61
        mamdani.addRule(Rule.parse("if p is O and q is H and r is L and s is H then t is MD", engine));//62
        mamdani.addRule(Rule.parse("if p is O and q is H and r is MD and s is L then t is MD", engine));//63
        mamdani.addRule(Rule.parse("if p is O and q is H and r is MD and s is H then t is SC", engine));//64
        mamdani.addRule(Rule.parse("if p is O and q is H and r is H and s is L then t is SC", engine));//65
        mamdani.addRule(Rule.parse("if p is O and q is H and r is H and s is H then t is SC", engine));//66
        mamdani.addRule(Rule.parse("if p is O and q is VH and r is L and s is L then t is MD", engine));//67
        mamdani.addRule(Rule.parse("if p is O and q is VH and r is L and s is H then t is SC", engine));//68
        mamdani.addRule(Rule.parse("if p is O and q is VH and r is MD and s is L then t is MD", engine));//69
        mamdani.addRule(Rule.parse("if p is O and q is VH and r is MD and s is H then t is SC", engine));//70
        mamdani.addRule(Rule.parse("if p is O and q is VH and r is H and s is L then t is SC", engine));//71
        mamdani.addRule(Rule.parse("if p is O and q is VH and r is H and s is H then t is SC", engine));//72
        
        mamdani.addRule(Rule.parse("if p is VO and q is L and r is L and s is L then t is HL", engine));//73
        mamdani.addRule(Rule.parse("if p is VO and q is L and r is L and s is H then t is HL", engine));//74
        mamdani.addRule(Rule.parse("if p is VO and q is L and r is MD and s is L then t is HL", engine));//75
        mamdani.addRule(Rule.parse("if p is VO and q is L and r is MD and s is H then t is MD", engine));//76
        mamdani.addRule(Rule.parse("if p is VO and q is L and r is H and s is L then t is MD", engine));//77
        mamdani.addRule(Rule.parse("if p is VO and q is L and r is H and s is H then t is MD", engine));//78MD
        mamdani.addRule(Rule.parse("if p is VO and q is MD and r is L and s is L then t is HL", engine));//79
        mamdani.addRule(Rule.parse("if p is VO and q is MD and r is L and s is H then t is MD", engine));//80
        mamdani.addRule(Rule.parse("if p is VO and q is MD and r is MD and s is L then t is MD", engine));//81
        mamdani.addRule(Rule.parse("if p is VO and q is MD and r is MD and s is H then t is SC", engine));//82
        mamdani.addRule(Rule.parse("if p is VO and q is MD and r is H and s is L then t is MD", engine));//83
        mamdani.addRule(Rule.parse("if p is VO and q is MD and r is H and s is H then t is SC", engine));//84
        mamdani.addRule(Rule.parse("if p is VO and q is H and r is L and s is L then t is HL", engine));//85
        mamdani.addRule(Rule.parse("if p is VO and q is H and r is L and s is H then t is MD", engine));//86
        mamdani.addRule(Rule.parse("if p is VO and q is H and r is MD and s is L then t is MD", engine));//87
        mamdani.addRule(Rule.parse("if p is VO and q is H and r is MD and s is H then t is SC", engine));//88
        mamdani.addRule(Rule.parse("if p is VO and q is H and r is H and s is L then t is MD", engine));//89
        mamdani.addRule(Rule.parse("if p is VO and q is H and r is H and s is H then t is SC", engine));//90
        mamdani.addRule(Rule.parse("if p is VO and q is VH and r is L and s is L then t is MD", engine));//91
        mamdani.addRule(Rule.parse("if p is VO and q is VH and r is L and s is H then t is SC", engine));//92
        mamdani.addRule(Rule.parse("if p is VO and q is VH and r is MD and s is L then t is MD", engine));//93
        mamdani.addRule(Rule.parse("if p is VO and q is VH and r is MD and s is H then t is SC", engine));//94
        mamdani.addRule(Rule.parse("if p is VO and q is VH and r is H and s is L then t is MD", engine));//95
        mamdani.addRule(Rule.parse("if p is VO and q is VH and r is H and s is H then t is SC", engine));//96
        
        */
        engine.addRuleBlock(mamdani);



        StringBuilder status = new StringBuilder();
        if (! engine.isReady(status))
            throw new RuntimeException("[engine error] engine is not ready:n" + status);

        InputVariable tempInput = engine.getInputVariable("p");
        InputVariable humiInput = engine.getInputVariable("q");
        InputVariable oxyInput = engine.getInputVariable("r");
        InputVariable partiInput = engine.getInputVariable("s");
        OutputVariable airConditionOutput = engine.getOutputVariable("t");

        tempInput.setValue(temperature);
        humiInput.setValue(humidity);
        oxyInput.setValue(oxygen);
        partiInput.setValue(particle);

        engine.process();

        String resultDisease=airConditionOutput.fuzzyOutputValue();
      //  System.out.println(resultDisease);
        
        String low = resultDisease.substring(0, 5);
        double lowAir = Double.valueOf(low);
    //    System.out.println(lowAir);
        String medium = resultDisease.substring(13, 17);
        double mediumAir = Double.valueOf(medium);
   //     System.out.println(mediumAir);
        String high = resultDisease.substring(27, 32);
        double highAir = Double.valueOf(high);
    //    System.out.println(highAir);
        
        
        if(lowAir > mediumAir && lowAir > highAir)
        {
            System.out.println("Air Condition Motor Speed is: LOW");
        }
        else if(mediumAir > highAir)
        {
          System.out.println("Air Condition Motor Speed is: Medium");
        }
        else
        {
            System.out.println("Air Condition Motor Speed is: HIGH");
        }
        
       // result.setText(resultDisease[0]);
       //  System.out.print("Result: ");
       // System.out.println(resultDisease);




    }
    
}
