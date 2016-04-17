import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Stack;


public class MainActivity extends ActionBarActivity {


    operation prev_instruction;
    TextView screen;
    TextView view_instructions;
    Button add, sub, mult, div, solve, clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize variables
        prev_instruction = null;

        //get textviews
        view_instructions = (TextView)findViewById(R.id.textview);
        screen = (TextView)findViewById(R.id.editText);

        //get buttons
        add = (Button)findViewById(R.id.add);
        sub = (Button)findViewById(R.id.sub);
        mult = (Button)findViewById(R.id.mult);
        div = (Button)findViewById(R.id.div);
        solve = (Button)findViewById(R.id.solve);
        clear = (Button)findViewById(R.id.clear);

        clear.setOnClickListener(new View.OnClickListener() {
            //clear the screnn and reset
            @Override
            public void onClick(View v) {
                screen.setText("");
                view_instructions.setText("");
                prev_instruction = null;
            }
        });

        solve.setOnClickListener(new View.OnClickListener() {
            //when = is click or evoke by other operations
            @Override
            public void onClick(View v) {
                if(prev_instruction != null) {
                    operationHelper('=');
                    prev_instruction.setAnswer(true);
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtract();
            }
        });

        mult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multiply();
            }
        });

        div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                divide();
            }
        });
    }

    public void add() {
        operationHelper('+');
    }

    public void subtract() {
        operationHelper('-');
    }

    public void multiply() {
        operationHelper('*');
    }

    public void divide() {
        operationHelper('/');
    }

    public void operationHelper(char operand) {
        //when no number is enter before operator
        if(screen.getText().toString().length() == 0 || screen.getText().toString().equals("")) {
            screen.setText("0");
        }

        try {
            if (prev_instruction == null) {     //is the first number entered

                if(operand == '=') {        //when one number an =
                    view_instructions.setText(screen.getText().toString() + " " + operand + " " + screen.getText().toString());
                } else {                //when one number and operand other than =
                    view_instructions.setText(screen.getText().toString() + " " + operand);
                }

                //save the number and operand into new operation variable
                prev_instruction = new operation(Double.parseDouble(screen.getText().toString()), operand);

            } else {

                if (prev_instruction.isAnswer()) {      //if the current number is an answer
                    if(operand == prev_instruction.getOpt()) {  //if = is click
                        if(Double.parseDouble(screen.getText().toString()) == prev_instruction.getNumber()) { //current number is the same as the answer
                            view_instructions.setText(view_instructions.getText().toString() + " " + operand + " " + prev_instruction.getNumber());
                        } else {        //user type in a new number
                            view_instructions.setText(screen.getText().toString() + " " + operand + " " + screen.getText().toString());
                            prev_instruction = new operation(Double.parseDouble(screen.getText().toString()), operand);
                        }
                    } else {
                        view_instructions.setText(view_instructions.getText().toString() + " " + operand);
                        prev_instruction = new operation(solve(), operand);
                    }
                } else if(operand == '=') {     // =  is click
                    view_instructions.setText(view_instructions.getText() + " " + screen.getText().toString() + " " + operand + " " + solve());
                    screen.setText(""+solve());
                    prev_instruction = new operation(solve(), operand);

                } else {    // +,-,*,/ is click
                    view_instructions.setText(view_instructions.getText().toString() + " " + screen.getText().toString() + " " + operand);
                    prev_instruction = new operation(solve(), operand);

                }

            }
        } catch (Exception e) {
            Toast.makeText(this, "Parse double failed(1)", Toast.LENGTH_SHORT).show();
        }

        //clear the screen when = is not clicked
        if(operand != '=') {
            screen.setText("");
        }
    }

    public double solve() {
        char operand = prev_instruction.getOpt();
        double number = prev_instruction.getNumber();
        double ans = 0;
        try {
            ans = Double.parseDouble(screen.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, "Parse double failed(2)", Toast.LENGTH_SHORT).show();
        }

        switch (operand) {
            case '+':
                ans = number + ans;
                break;
            case '-':
                ans = number - ans;
                break;
            case '*':
                ans = number * ans;
                break;
            case '/':
                if(ans == 0) {
                    Toast.makeText(this, "Can't divide by 0", Toast.LENGTH_SHORT).show();
                }
                ans = number / ans;
                break;
            default:
        }
        return ans;
    }

    public void numberClick(View v) {
        if(prev_instruction != null && prev_instruction.isAnswer()) {
            screen.setText("");
            view_instructions.setText("");
            prev_instruction = new operation(prev_instruction.getNumber(), prev_instruction.getOpt());
        }
        switch (v.getId()) {
            case R.id.zero:
                screen.setText(screen.getText().toString() + '0');
                break;
            case R.id.one:
                screen.setText(screen.getText().toString() + '1');
                break;
            case R.id.two:
                screen.setText(screen.getText().toString() + '2');
                break;
            case R.id.three:
                screen.setText(screen.getText().toString() + '3');
                break;
            case R.id.four:
                screen.setText(screen.getText().toString() + '4');
                break;
            case R.id.five:
                screen.setText(screen.getText().toString() + '5');
                break;
            case R.id.six:
                screen.setText(screen.getText().toString() + '6');
                break;
            case R.id.seven:
                screen.setText(screen.getText().toString() + '7');
                break;
            case R.id.eight:
                screen.setText(screen.getText().toString() + '8');
                break;
            case R.id.nine:
                screen.setText(screen.getText().toString() + '9');
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
