package repository;

import model.Seguro;

import java.util.ArrayList;
import java.util.List;

public final class SeguroDAO {

    static List<Seguro> seguros = new ArrayList<>();

    public static void salvar(Seguro seguro) {
        seguros.add(seguro);
    }

    public static List<Seguro> buscarTodos() {
        System.out.println(seguros);
        return seguros;
    }

    public static List<Seguro> buscarPorNome(String nome) {
        List<Seguro> filtradas = new ArrayList<>();
        for (Seguro seguro : seguros) {
            if (seguro.getSegurado().getNome().contains(nome)) {
                filtradas.add(seguro);
            }
        }
        return filtradas;
    }

    public static Object[] findSegurosInArray() {
        List<Seguro> seguros = SeguroDAO.buscarTodos();
        List<String> segurosNomes = new ArrayList<>();

        for (Seguro seguro : seguros) {
            segurosNomes.add(seguro.getSegurado().getNome());
        }

        return segurosNomes.toArray();
    }

}
