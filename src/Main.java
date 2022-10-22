import model.*;
import repository.PessoaDAO;
import repository.SeguradoraDAO;
import repository.SeguroDAO;
import repository.UsuarioDAO;

import javax.swing.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Object usuarioLogado = chamaSelecaoUsuario();
        checaSenhaUsuario(usuarioLogado);
    }

    private static void chamaMenuCadastros() {
        String[] opcoesMenuCadastro = {"Pessoa", "Seguradora", "Seguro", "Voltar"};
        int menuCadastro = JOptionPane.showOptionDialog(null, "Escolha uma opção:",
                "Menu Cadastros",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesMenuCadastro, opcoesMenuCadastro[0]);

        switch (menuCadastro) {
            case 0: //Pessoa
                Pessoa pessoa = chamaCadastroPessoa();
                PessoaDAO.salvar(pessoa);
                chamaMenuCadastros();
                break;
            case 1: //Seguradoras
                Seguradora seguradora = chamaCadastroSeguradora();
                SeguradoraDAO.salvar(seguradora);
                chamaMenuCadastros();
                break;
            case 2: //Seguro
                Seguro seguro = chamaCadastroSeguro();
                SeguroDAO.salvar(seguro);
                chamaMenuCadastros();
                break;
            case 3: //Voltar
                chamaMenuPrincipal();
                break;
        }
    }

    private static Pessoa chamaCadastroPessoa() {
        String[] opcaoPessoas = {"Fisica", "Juridica"};
        String nome = JOptionPane.showInputDialog(null, "Digite o nome da pessoa: ");
        int tipoPessoa = JOptionPane.showOptionDialog(null, "Escolha uma opção:",
                "Tipo Pessoa",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcaoPessoas, opcaoPessoas[0]);
        String tipoDocumento = "CPF";
        if (tipoPessoa == 1) {
            tipoDocumento = "CNPJ";
        }
        String documento = JOptionPane.showInputDialog(null, "Digite o " + tipoDocumento + " da pessoa: ");

        if (tipoDocumento.equals("CPF")) {
            PessoaFisica pessoaFisica = new PessoaFisica();
            pessoaFisica.setTipo(TipoPessoa.FISICA);
            pessoaFisica.setNome(nome);
            pessoaFisica.setCpf(documento);
            return pessoaFisica;
        } else {
            PessoaJuridica pessoaJuridica = new PessoaJuridica();
            pessoaJuridica.setTipo(TipoPessoa.JURIDICA);
            pessoaJuridica.setNome(nome);
            pessoaJuridica.setCnpj(documento);
            return pessoaJuridica;
        }
    }

    private static Seguradora chamaCadastroSeguradora() {
        String nome = JOptionPane.showInputDialog(null, "Digite o nome da seguradora: ");
        String endereco = JOptionPane.showInputDialog(null, "Digite o endereço da seguradora: ");
        String site = JOptionPane.showInputDialog(null, "Digite o site da seguradora: ");

        Seguradora seguradora = new Seguradora();
        seguradora.setNome(nome);
        seguradora.setEndereco(endereco);
        seguradora.setSite(site);

        return seguradora;

    }

    private static Seguro chamaCadastroSeguro() {

        Object[] selectionValues = PessoaDAO.findPessoasInArray();
        String initialSelection = (String) selectionValues[0];
        Object selection = JOptionPane.showInputDialog(null, "Selecione o cliente do seguro?",
                "SeguradoraAPP", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
        List<Pessoa> pessoas = PessoaDAO.buscarPorNome((String) selection);


        Object[] selectionValuesSeguradora = SeguradoraDAO.findSeguradoraInArray();
        String initialSelectionSeguradora = (String) selectionValues[0];
        Object selectionSeguradora = JOptionPane.showInputDialog(null, "Selecione o cliente do seguro?",
                "SeguradoraAPP", JOptionPane.QUESTION_MESSAGE, null, selectionValuesSeguradora, initialSelectionSeguradora);
        List<Seguradora> seguradoras = SeguradoraDAO.buscarPorNome((String) selectionSeguradora);


        String[] opcaoSeguro = {"Automotivo", "Residencial", "Vida"};
        int tipoSeguro = JOptionPane.showOptionDialog(null, "Escolha uma opção:",
                "Tipo Pessoa",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcaoSeguro, opcaoSeguro[0]);

        String valorApolice = JOptionPane.showInputDialog(null, "Digite o valor da apolice de seguro: ");
        String valorPremio = JOptionPane.showInputDialog(null, "Digite o valor do premio da apolice: ");

        Seguro seguro = new Seguro();
        seguro.setSegurado(pessoas.get(0));
        seguro.setTipo(TipoSeguro.getTipoById(tipoSeguro));
        seguro.setSeguradora(seguradoras.get(0));
        seguro.setValorApolice(BigDecimal.valueOf(Double.valueOf(valorApolice)));
        seguro.setValorPremio(BigDecimal.valueOf(Double.valueOf(valorPremio)));

        return seguro;

    }

    private static void chamaMenuProcessos() {
        String[] opcoesMenuProcesso = {"Gerar Sinistro", "Baixar Seguro", "Voltar"};
        int menu_processos = JOptionPane.showOptionDialog(null, "Escolha uma opção:",
                "Menu Processos",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesMenuProcesso, opcoesMenuProcesso[0]);

        switch (menu_processos) {
            case 0: //Gerar Sinistro
                Seguro seguro = processarSinistro();
                SeguroDAO.salvar(seguro);
                chamaMenuProcessos();
                break;
            case 1: //Baixar Seguro
                Seguro seguro2 = processarBaixa();
                SeguroDAO.salvar(seguro2);
                chamaMenuProcessos();
                break;
            case 2: //Voltar
                chamaMenuPrincipal();
                break;
        }
    }

    private static Seguro processarSinistro() {
        Object[] selectionValues = SeguroDAO.findSegurosInArray();
        String initialSelection = (String) selectionValues[0];
        Object selection = JOptionPane.showInputDialog(null, "Selecione o seguro?",
                "SeguradoraAPP", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
        List<Seguro> seguros = SeguroDAO.buscarPorNome((String) selection);
        String motivoSinistro = JOptionPane.showInputDialog(null, "Digite o motivo do sinistro: ");

        Seguro seguro = seguros.get(0);
        seguro.setMotivoSinistro(motivoSinistro);
        seguro.setDataSinistro(LocalDate.now());
        seguro.setTemSinistro(Boolean.TRUE);

        return seguro;
    }

    private static Seguro processarBaixa() {
        Object[] selectionValues = SeguroDAO.findSegurosInArray();
        String initialSelection = (String) selectionValues[0];
        Object selection = JOptionPane.showInputDialog(null, "Selecione o seguro?",
                "SeguradoraAPP", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
        List<Seguro> seguros = SeguroDAO.buscarPorNome((String) selection);
        String motivoSinistro = JOptionPane.showInputDialog(null, "Digite o motivo da baixa: ");

        Seguro seguro = seguros.get(0);
        seguro.setMotivoBaixa(motivoSinistro);
        seguro.setBaixado(Boolean.TRUE);

        return seguro;
    }

    private static void chamaMenuRelatorios() {
        String[] opcoesMenuProcesso = {"Pessoas", "Seguradoras", "Seguros", "Voltar"};
        int menu_processos = JOptionPane.showOptionDialog(null, "Escolha uma opção:",
                "Menu Relatórios",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesMenuProcesso, opcoesMenuProcesso[0]);

        switch (menu_processos) {
            case 0: //Pessoa
                chamaRelatorioPessoa();
                chamaMenuRelatorios();
                break;
            case 1: //Seguradoras
                chamaRelatorioSeguradora();
                chamaMenuRelatorios();
                break;
            case 2: //Seguros
                chamaRelatorioSeguro();
                chamaMenuRelatorios();
                break;
            case 3: //Voltar
                chamaMenuPrincipal();
                break;
        }
    }

    private static void chamaRelatorioPessoa() {
        List<Pessoa> pessoas = PessoaDAO.buscarTodos();
        String listaPessoas = "Lista de Pessoas";
        for (Pessoa pessoa : pessoas) {
            listaPessoas += "\n" + pessoa.getNome() + "  tipo: " + pessoa.getTipo() + "   documento: " + pessoa.getDocumento();
        }
        JOptionPane.showMessageDialog(null, listaPessoas);
    }

    private static void chamaRelatorioSeguradora() {
        List<Seguradora> seguradoras = SeguradoraDAO.buscarTodos();
        String lista = "Lista de Pessoas";
        for (Seguradora item : seguradoras) {
            lista += "\n" + item.getNome() + "  Endereço: " + item.getEndereco() + "   Site: " + item.getSite();
        }
        JOptionPane.showMessageDialog(null, lista);
    }

    private static void chamaRelatorioSeguro() {
        List<Seguro> seguros = SeguroDAO.buscarTodos();
        String lista = "Lista de Pessoas";
        for (Seguro seguro : seguros) {
            lista += "\n" + seguro.getSegurado().getNome() + "  tipo: " + seguro.getTipo() + "   seguradora: " + seguro.getSeguradora().getNome();
        }
        JOptionPane.showMessageDialog(null, lista);
    }

    private static void chamaMenuPrincipal() {
        String[] opcoesMenu = {"Cadastros", "Processos", "Relatorios", "Sair"};
        int opcao = JOptionPane.showOptionDialog(null, "Escolha uma opção:",
                "Menu Principal",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoesMenu, opcoesMenu[0]);
        switch (opcao) {
            case 0: //Cadastros
                chamaMenuCadastros();
                break;
            case 1: //Processos
                chamaMenuProcessos();
                break;
            case 2: //Relatorios
                chamaMenuRelatorios();
                break;
            case 3: //SAIR

                break;
        }
    }

    private static void checaSenhaUsuario(Object usuarioLogado) {
        String senhaDigitada = JOptionPane.showInputDialog(null, "Informe a senha do usuario (" + usuarioLogado + ")");
        Usuario usuarioByLogin = UsuarioDAO.findUsuarioByLogin((String) usuarioLogado);

        if (usuarioByLogin.getSenha().equals(senhaDigitada)) {
            chamaMenuPrincipal();
        } else {
            JOptionPane.showMessageDialog(null, "Senha incorreta!");
            checaSenhaUsuario(usuarioLogado);
        }
    }


    private static Object chamaSelecaoUsuario() {
        Object[] selectionValues = UsuarioDAO.findUsuariosSistemaInArray();
        String initialSelection = (String) selectionValues[0];
        Object selection = JOptionPane.showInputDialog(null, "Selecione o usuario?",
                "SeguradoraAPP", JOptionPane.QUESTION_MESSAGE, null, selectionValues, initialSelection);
        return selection;
    }
}