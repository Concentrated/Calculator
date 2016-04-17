public class operation {

    private boolean answer;
    private double number;
    private char opt;

    operation(double number, char operation) {
        this.number = number;
        this.opt = operation;
        this.answer = false;
    }

    public double getNumber() {
        return number;
    }

    public char getOpt() {
        return opt;
    }

    public boolean isAnswer() { return answer; }

    public void setAnswer(boolean ans) {
        answer = ans;
    }
}
