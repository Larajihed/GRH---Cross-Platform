/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.views.PieChart;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;

/**
 *
 * @author usoum
 */
public class statForm extends Form{
    public statForm(int a,int r,int att){
        super("", BoxLayout.y());
        int score = a+r+att;
        String rr ="Le nombre total des candidat est "+score;
        TextArea te = new TextArea(rr);
        add(te);
        Button can = new Button("<--retour");
                can.addActionListener(e->{
            ListCandidatForm cf = new ListCandidatForm();
            cf.show();
        });
        createPieChartForm(a,r,att);
        this.add(can);
    }
    public DefaultRenderer buildCatRendrer(int []colors) {
        
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        renderer.setMargins(new int[] {0, 0, 0, 0});
        
        for(int color : colors) {
            SimpleSeriesRenderer simpleSeriesRenderer = new SimpleSeriesRenderer();
            simpleSeriesRenderer.setColor(color);
            renderer.addSeriesRenderer(simpleSeriesRenderer);
        }
        return renderer;
     }  
    
    
      public void createPieChartForm(int a ,int r , int at) {
        
        //chna3ml stat feedback par rapport l reclamation 
        double total = a+r+at;
        
        //values
        int prcntrefus = r;
        
        int prcntacc = a;
        
        int prcntatt = at;
         
        
        //colors set:
        int[]colors = new int[]{0x176eb0,0x008000,0xfd4c49};
        
        DefaultRenderer renderer = buildCatRendrer(colors);
        renderer.setLabelsColor(0x000000); // black color for labels.
        
        renderer.setZoomButtonsVisible(true);//zoom
        renderer.setLabelsTextSize(40);
        renderer.setZoomEnabled(true);
        renderer.setChartTitleTextSize(20);
        renderer.setDisplayValues(true);
        renderer.setShowLabels(true);
        SimpleSeriesRenderer rr = renderer.getSeriesRendererAt(0);
        rr.setHighlighted(true);
        
        //CREATe the chart ...
        PieChart chart = new PieChart(buildDataset("title",Math.round(prcntrefus),Math.round(prcntacc),Math.round(prcntatt)), renderer);
        
        // n7oto chart fi component
        ChartComponent c  = new ChartComponent(chart);
        
        String []messages = {
            ""
        };
        
        SpanLabel message = new SpanLabel(messages[0], "WelcomeMessage");
        
        Container cnt = BorderLayout.center(message);
        cnt.setUIID("Container");
        add(cnt);
        add(c);
        
    }
        private CategorySeries buildDataset(String title, int prcntrefus, int prcntacc , int prcntatt) {
        
        CategorySeries series = new CategorySeries(title);
        
        series.add("Refusée",prcntrefus);
        series.add("Accepter",prcntacc);
        series.add("En Attente",prcntatt);
        
        return series;
    }
    
}
