import javax.swing.*;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import static java.lang.Integer.MAX_VALUE;
public class Draw extends JFrame {
    public Draw(int[][] matrica)
    {
        mxGraph graph = new mxGraph();
        Object defaultParent = graph.getDefaultParent();
        graph.getModel().beginUpdate();
        graph.getModel().endUpdate();
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
        Object ob[] = new Object[matrica.length];
        for (int i = 1; i < matrica.length; i++)
            ob[i] = graph.insertVertex(defaultParent, null, i, 100 + (i*100), 100, 50, 50,"Цвет обводки = красный");
        for (int i=0; i<matrica.length; i++)
        {
            for (int j = 0; j < matrica.length; j++)
            {
                if (matrica[i][j] != 0 && matrica[i][j] != 999)
                    graph.insertEdge(defaultParent, null, matrica[i][j], ob[i],ob[j]);
            }
        }
        graph.setCellsEditable(false);
        graph.setCellsMovable(true);
        graph.setCellsResizable(false);
        graph.setCellsSelectable(true);
        graph.setEnabled(false);
        graphComponent.setConnectable(false);
    }
}