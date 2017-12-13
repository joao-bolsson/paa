package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @since 2017, 12 Dec.
 */
public class FilesJ {

    private int numberOfNodes;
    private int[][] matDistance;
    private int[][] matConnection;

    /**
     * Default construct.
     *
     * @param path File path to read.
     */
    public FilesJ(final String path) {
        readArq(path);
    }

    /**
     * @return The number of nodes readed from file.
     */
    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    /**
     * @return The distance matrix.
     */
    public int[][] getMatDistance() {
        return matDistance;
    }

    /**
     * @return The connection matrix.
     */
    public int[][] getMatConnection() {
        return matConnection;
    }

    private void readArq(final String arq) {
        File arquivo = new File(arq);
        FileReader arqLeitura;
        BufferedReader leitor;
        String linha;
        String[] val;
        try {
            arqLeitura = new FileReader(arquivo);
            leitor = new BufferedReader(arqLeitura);

            try {
                //leio primeira linha
                linha = leitor.readLine();
                numberOfNodes = Integer.parseInt(linha);
                //crio as matrizez
                matConnection = new int[numberOfNodes][numberOfNodes];
                matDistance = new int[numberOfNodes][numberOfNodes];

                //pulo uma linha na leitura
                linha = leitor.readLine();
                //leio a matriz de distancias
                for (int i = 0; i < numberOfNodes; i++) {
                    linha = leitor.readLine();
                    val = linha.split(" ");//quebro os dados
                    for (int j = 0; j < numberOfNodes; j++) {
                        matDistance[i][j] = Integer.parseInt(val[j]);
                    }
                }

                //pulo uma linha na leitura
                linha = leitor.readLine();

                //leio a matriz de Conexao
                for (int i = 0; i < numberOfNodes; i++) {
                    linha = leitor.readLine();
                    val = linha.split(" ");//quebro os dados
                    for (int j = 0; j < numberOfNodes; j++) {
                        matConnection[i][j] = Integer.parseInt(val[j]);
                    }
                }

            } catch (final IOException e) {
                System.out.println("Falha ao ler o aquivo: " + e);
            }
        } catch (final FileNotFoundException e) {
            System.out.println("Falha ao ler o aquivo: " + e);
        }
    }
}
