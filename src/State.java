import java.util.ArrayList;
import java.util.LinkedList;

public class State {

    private Graph graph;
    private int selectedNodeId;
    private State parentState;

    public State(Graph graph, int selectedNodeId, State parentState){
        this.graph= graph.copy();
        this.selectedNodeId= selectedNodeId;
        if(parentState != null){
            this.parentState= parentState;
        }else{
            this.parentState = null;
        }
    }

    public ArrayList<State> successor(){
        ArrayList<State> children= new ArrayList<State>();
        for (int i = 0; i < this.graph.size(); i++) {
            int nodeId= this.graph.getNode(i).getId();
            if(nodeId != selectedNodeId){
                State newState = new State(this.graph.copy(), nodeId, this);
                LinkedList<Integer> nodeNeighbors = newState.getGraph().getNode(nodeId).getNeighborsIds();
                for (int j = 0; j < nodeNeighbors.size(); j++) {
                    int neighborId=nodeNeighbors.get(j);
                    newState.getGraph().getNode(neighborId).reverseNodeColor();
                }
                if(newState.getGraph().getNode(nodeId).getColor() == Color.Black){
                    int greenNeighborsCount=0;
                    int redNeighborsCount=0;
                    int blackNeighborcount=0;
                    for (int j = 0; j < nodeNeighbors.size(); j++) {
                        int neighborId=nodeNeighbors.get(j);
                        Color c = newState.getGraph().getNode(neighborId).getColor();
                        if (c.equals(Color.Red)) {
                            redNeighborsCount++;
                        } else if (c.equals(Color.Green)) {
                            greenNeighborsCount++;
                        } else if (c.equals(Color.Black)) {
                            blackNeighborcount++;
                        }
                        
                    }
                    if(greenNeighborsCount > redNeighborsCount && greenNeighborsCount > blackNeighborcount){
                        newState.getGraph().getNode(nodeId).changeColorTo(Color.Green);
                    }else if(redNeighborsCount > greenNeighborsCount && redNeighborsCount > blackNeighborcount){
                        newState.getGraph().getNode(nodeId).changeColorTo(Color.Red);
                    }
                }else {
                    newState.getGraph().getNode(nodeId).reverseNodeColor();
                }
                children.add(newState);
            }
        }
        return children;
    }

    public void removeChild(int neighborId) {
        this.getGraph().removeLink(this.selectedNodeId, neighborId);
    }

    public String hash(){
        String result= "";
        for (int i = 0; i < graph.size(); i++) {

            Color c = graph.getNode(i).getColor();
            if (c.equals(Color.Red)) {
                result += "r";
            } else if (c.equals(Color.Green)) {
                result += "g";
            } else if (c.equals(Color.Black)) {
                result += "b";
            }
    
        }
        return result;
    }

    public String outputGenerator(){
        String result= "";
        for (int i = 0; i < graph.size(); i++) {
            Color c = graph.getNode(i).getColor();
            String color = "";
            if (c.equals(Color.Red)) {
                color = "R";
            } else if (c.equals(Color.Green)) {
                color = "G";
            } else if (c.equals(Color.Black)) {
                color = "B";
            }
            
            result += graph.getNode(i).getId()+color+" ";
            for (int j = 0; j < graph.getNode(i).getNeighborsIds().size(); j++) {
                int neighborId=graph.getNode(i).getNeighborsId(j);
                Color color2 = graph.getNode(neighborId).getColor();
                String neighborColor = "";
                if (color2.equals(Color.Red)) {
                    neighborColor = "R";
                } else if (color2.equals(Color.Green)) {
                    neighborColor = "G";
                } else if (color2.equals(Color.Black)) {
                    neighborColor = "B";
                }
                
                result += neighborId+neighborColor+" ";
            }
            result += ",";
        }
        return result;
    }

    public Graph getGraph(){
        return graph;
    }

    public State getParentState(){
        return parentState;
    }

    public  int getSelectedNodeId(){
        return selectedNodeId;
    }

    public int getDepth() {
        int count = 0;
        State parent = this.getParentState();
        while(parent != null) {
            count++;
            parent = parent.getParentState();
        }

        return count;
    }
}
