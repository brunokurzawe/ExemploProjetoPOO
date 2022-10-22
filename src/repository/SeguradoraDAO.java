package repository;

import model.Seguradora;

import java.util.ArrayList;
import java.util.List;

public final class SeguradoraDAO {

    static List<Seguradora> seguradoras = new ArrayList<>();

    public static void salvar(Seguradora seguradora) {
        seguradoras.add(seguradora);
    }

    public static List<Seguradora> buscarTodos() {
        System.out.println(seguradoras);
        return seguradoras;
    }

    public static List<Seguradora> buscarPorNome(String nome) {
        List<Seguradora> filtradas = new ArrayList<>();
        for (Seguradora seguradora : seguradoras) {
            if (seguradora.getNome().contains(nome)) {
                filtradas.add(seguradora);
            }
        }
        return filtradas;
    }

    public static Object[] findSeguradoraInArray() {
        List<Seguradora> seguradoras = SeguradoraDAO.buscarTodos();
        List<String> seguradorasNomes = new ArrayList<>();

        for (Seguradora seguradora : seguradoras) {
            seguradorasNomes.add(seguradora.getNome());
        }

        return seguradorasNomes.toArray();
    }

}
