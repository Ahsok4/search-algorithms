import java.util.*;

class RState implements Comparable<RState>{
    private State state;
    private int g;
    private int h;
    private int f;

    public RState(State state, int g, int h) {
        this.state = state;
        this.h = h;
        this.g = g;
        this.f = h+g;
    }

    public int compareTo(RState g2) {
        return this.f() - g2.f();
    }

    public State getState() {
        return this.state;
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

    public void setF(int f) {
        this.f = f;
    }

    public void setH(int x) {
        this.h = x;
    }

    public void setG(int x) {
        this.g = x;
    }

}
public class RBFS {

    public static void search(State state) {
        rbfs(new RState(state, 0, H.getH(state)), Integer.MAX_VALUE);
    }

    public static int rbfs(RState initialState, int f_limit) {

        if (Check.isGoal(initialState.getState())) {
            Check.result(initialState.getState(), "RbfsResult");
            return -1;
        }

        ArrayList<State> successors = initialState.getState().successor();
        ArrayList<RState> rSuccessors = new ArrayList<>();

        for (State s: successors) {
            RState rState;
            rState = new RState(s, initialState.getG() + H.getH(s), H.getH(s));
            rSuccessors.add(rState);
        }

        if (rSuccessors.size() == 0) {
            return Integer.MAX_VALUE;
        }

        RState best;

        do {
            Collections.sort(rSuccessors);
            best = rSuccessors.get(0);

            if (best.f() > f_limit || best.f() == Integer.MAX_VALUE)
                break;
    
            RState alternative = rSuccessors.get(1);
            int newFLimit = Math.min(f_limit, alternative.f());

            int result = rbfs(best, newFLimit);

            if (result == -1) {
                return -1;
            }

            best.setF(result);
            
        } while (true);

        return best.f();
    }
}