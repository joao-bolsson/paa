package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author João Bolsson (jvmarques@inf.ufsm.br)
 * @since 2017, 12 Dec.
 */
public final class Control {

    private static final String FILE_PATH = Control.class.getClassLoader().getResource("resource/teste.txt").getPath();

    private final List<Node> frontiers, visited, unknow;

    private int[][] distances, connections;

    private final FilesJ file;

    private int numberOfNodes;
    private Node startNode, finalNode, currentNode;

    /**
     * Default construct.
     */
    public Control() {
        frontiers = new ArrayList<>();
        visited = new ArrayList<>();
        unknow = new ArrayList<>();

        file = new FilesJ(FILE_PATH);
    }

    private void init(final int startNodeId, final int finalNodeId) {
        frontiers.clear();
        visited.clear();
        unknow.clear();

        numberOfNodes = file.getNumberOfNodes();
        distances = file.getMatDistance();
        connections = file.getMatConnection();

        for (int i = 0; i < numberOfNodes; i++) {
            unknow.add(new Node(i));
        }

        startNode = getNode(unknow, startNodeId);
        //recupero o no final
        finalNode = getNode(unknow, finalNodeId);
        //removo da lista de desconhecidos
        unknow.remove(startNodeId);
        //no corrente e o no inicial
        currentNode = startNode;
    }

    /**
     * Finds the best way between startNode and finalNode.
     *
     * @param startNodeId Start node.
     * @param finalNodeId Final node.
     */
    public void findPath(final int startNodeId, final int finalNodeId) {
        init(startNodeId, finalNodeId);

        while (finalNode != currentNode) {
            //insiro o no corrente em visitados
            visited.add(currentNode);
            frontiers.remove(currentNode);
            //insiro os visinhos do corrente na fronteira
            insereFronteiras(currentNode.getId());
            //recupera o proximo no
            currentNode = nextNode();
            if (currentNode == null) {
                System.out.println("Nao Existe caminho.");
                return;
            }
        }

        //cheguei ate o no final, remonto o caminho de volta
        Node aux = finalNode;
        int distancia = finalNode.getCurrentWeight();
        String caminho = "";
        while (aux != null) {
            caminho = (aux.getId() + 1) + " " + caminho;
            aux = aux.getPrev();
        }

        System.out.println("Caminho: " + caminho);
        System.out.println("Distancia: " + distancia);
    }

    private void insereFronteiras(final int id) {
        Node aux;
        for (int i = 0; i < numberOfNodes; i++) {
            if (connections[id][i] > 1) {
                //ou seja, somente visinhos
                aux = getNode(unknow, i);
                int novoPeso = connections[id][i] + currentNode.getCurrentWeight();
                //[dist Prox] + [pesoCorrente]
                if (aux != null) {
                    //se ainda for desconhecido
                    if (!visited.contains(aux)) {
                        //se nao foi visitado, adc a fronteiras, removo do desconhecido e atualizo peso 
                        aux.setCurrentWeight(novoPeso);
                        aux.setPrev(currentNode); //seto anterior o corrente
                        frontiers.add(aux);
                        unknow.remove(aux);
                    }
                } else {
                    //se ja e conhecido
                    aux = getNode(frontiers, i);
                    if (aux != null) {
                        //se esta na fronteira
                        if (aux.getCurrentWeight() > novoPeso) {
                            //se atual melhor que o antigo
                            aux.setCurrentWeight(novoPeso);
                            aux.setPrev(currentNode);
                        }
                    }
                }
            }
        }
    }

    private Node nextNode() {
        if (frontiers.isEmpty()) {
            //se nao tiver ninguem na fronteira
            return null;
        }
        //primeiro da lista
        Node prox = frontiers.get(0);

        int menor = prox.getCurrentWeight() + distances[prox.getId()][finalNode.getId()];
        //[peso corrente]+[distancia ate final] ==> para converger para o local certo 
        //percorro a fronteira
        for (Node cur : frontiers) {
            int aux = distances[cur.getId()][finalNode.getId()]; //[distancia ate o final -- linha reta]
            if ((cur.getCurrentWeight() + aux) < menor) {
                //se atual melhor que menor
                menor = cur.getCurrentWeight() + aux;
                prox = cur;
            }
        }
        return prox;
    }

    private Node getNode(final List<Node> list, final int id) {
        for (Node cur : list) {
            if (cur.getId() == id) {
                return cur;
            }
        }
        return null;
    }

    /**
     * Mian function.
     *
     * @param args Command line aruments (ignored).
     */
    public static void main(final String[] args) {
        Control control = new Control();

        boolean stop = false;

        while (!stop) {
            Scanner entrada = new Scanner(System.in);

            int start, end;
            System.out.print("Início: ");
            start = entrada.nextInt();

            System.out.print("Fim: ");
            end = entrada.nextInt();

            control.findPath(start - 1, end - 1);
            System.out.print("Parar?[s/n] ");
            String ans = entrada.nextLine();

            stop = ans.startsWith("s");
        }
    }
}
