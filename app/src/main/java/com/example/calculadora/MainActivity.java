package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {

    TextView display;
    String expressao = "";
    ArrayList<String> historico = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        View root = findViewById(android.R.id.content);
        ArrayList<Button> botoes = new ArrayList<>();
        pegarBotoes(root, botoes);

        for (Button b : botoes) {
            b.setOnClickListener(v -> {
                String texto = b.getText().toString();
                if (texto.equals("=")) {
                    calcular();
                } else {
                    expressao += converter(texto);
                    display.setText(expressao);
                }
            });
        }
    }

    private void pegarBotoes(View v, ArrayList<Button> lista) {
        if (v instanceof Button) {
            lista.add((Button) v);
        } else if (v instanceof android.view.ViewGroup) {
            for (int i = 0; i < ((android.view.ViewGroup) v).getChildCount(); i++) {
                pegarBotoes(((android.view.ViewGroup) v).getChildAt(i), lista);
            }
        }
    }

    private String converter(String texto) {
        switch (texto) {
            case "√": return "sqrt(";
            case "^": return "^";
            case "sin": return "sin(";
            case "cos": return "cos(";
            case "tan": return "tan(";
            case "log": return "log(";
            default: return texto;
        }
    }

    private void calcular() {
        try {
            Expression expression = new ExpressionBuilder(expressao).build();
            double resultado = expression.evaluate();

            // Verifica se o resultado é inteiro
            if (resultado == (int) resultado) {
                int resultadoInt = (int) resultado;
                historico.add(expressao + " = " + resultadoInt);
                display.setText(String.valueOf(resultadoInt));
                expressao = String.valueOf(resultadoInt);
            } else {
                historico.add(expressao + " = " + resultado);
                display.setText(String.valueOf(resultado));
                expressao = String.valueOf(resultado);
            }

        } catch (Exception e) {
            display.setText("Erro");
            expressao = "";
        }
    }
}