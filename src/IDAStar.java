import java.util.*;

class IState {

    private int h;
    private int g;
    private int f;

    private State state;

    IState(State state, int h, int g) {
        this.h = h;
        this.g = g;
        this.state = state;
        f = h + g;
    }

    public int getH() {
        return this.h;
    }
    
    public int getG() {
        return this.g;
    }

    public int f() {
        return this.f;
    }

    public State getState() {
        return this.state;
    }
    
}

public class IDAStar {

    public static void search(State state) {

        IState iState = new IState(state, H.getH(state), 0);
        ArrayList<IState> path = new ArrayList<IState>();
        path.add(iState);
        int initialCutoff = iState.getH();
        int cutoff;

        while(true) {
            cutoff = recursive(path, initialCutoff);
            if(Check.isGoal(path.get(path.size()-1).getState())) {
                Check.result(path.get(path.size()-1).getState(), "IdastarResult");
                break;
            }
            initialCutoff = cutoff;

            if(initialCutoff == Integer.MAX_VALUE) {
                break;
            }
        }
    }

    private static int recursive(ArrayList<IState> path, int cutoff) {

        IState state = path.get(path.size()-1);

        if(state.f() > cutoff) {
            return state.f();
        }

        if(Check.isGoal(state.getState())) {
            return -1;
        }

        int min = Integer.MAX_VALUE;
        ArrayList<State> successors = state.getState().successor();

        for (State s : successors) {
            IState child = new IState(s, H.getH(s), state.getG()+1);
            if(!path.contains(child)) {
                path.add(child);
                int minf = recursive(path, cutoff);

                if(minf == -1) {
                    return -1;
                }
                
                if(min > minf) {
                    min = minf;
                }
                
                path.remove(path.size()-1);
            }
        }

        return min;
    }

}
