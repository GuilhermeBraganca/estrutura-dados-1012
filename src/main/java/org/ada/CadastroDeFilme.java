package org.ada;


import org.ada.dto.Filme;
import org.ada.map.Mapa;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class CadastroDeFilme {
    private int ultimoIdFuncionario = 0;
    private List<Filme> listaDeFilmes = new LinkedList<>();
    private Mapa<Integer, Filme> mapaIdFilme = new Mapa<>();
    private Mapa<String, Filme> mapaFilmePorNome = new Mapa<>();

    private final EntradaDeDados leitor;
    private final String DIGITE_OPCAO_DESEJADA = "Digite a opção desejada: ";
    private final String OPCAO_SAIR = "x";
    private final String OPCAO_CADASTRAR_FILME = "1";
    private final String OPCAO_LISTAR_FILMES = "2";
    private final String OPCAO_CADASTRAR_EM_LOTE = "3";
    private final String OPCAO_BUSCA_POR_ID = "4";
    private final String OPCAO_BUSCA_POR_TITLE = "5";

    public CadastroDeFilme(EntradaDeDados leitor){
        this.leitor = leitor;
        iniciaApp();
    }

    public void processar(){

            String opcaoDigitada = obterEntradaDoUsuario(leitor);

            while(!escolheuSair(opcaoDigitada)){
                tratarOpcaoSelecionada(opcaoDigitada);
                opcaoDigitada = obterEntradaDoUsuario(leitor);
            }

        finalizaApp();

    }

    private void tratarOpcaoSelecionada(String opcaoDigitada) {
        switch (opcaoDigitada){
            case OPCAO_SAIR:
                break;
            case OPCAO_CADASTRAR_FILME:
                this.inserirFilme(construirFilme(++ultimoIdFuncionario, leitor));
                System.out.println("Cadastro realizado com sucesso!");
                pularLinha(2);
                break;
            case OPCAO_LISTAR_FILMES:
                listarFilmes();
                pularLinha(2);
                break;
            case OPCAO_CADASTRAR_EM_LOTE:
                carregarFilmesEmLote();
                break;
            case OPCAO_BUSCA_POR_ID:
                buscaPorIdHashMap();
                break;
            case OPCAO_BUSCA_POR_TITLE:
                buscaPorTitleHashMap();
                break;
            default:
                opcaoInvalida();
                break;
        }
    }

    private List<Filme> removerDuplicados(List<Filme> lista){
        return new ArrayList<>(new HashSet<>(lista));
    }

    private void carregarFilmesEmLote(){
        List<Filme> novosFilmes =
                new CarregarDadosExternos().carregarFilmesCVS();

        this.inserirFilme(removerDuplicados(novosFilmes));

    }

    private void inserirFilme(List<Filme> filmes){
        for (Filme filme: filmes){
            inserirFilme(filme);
        }
    }

    private void inserirFilme(Filme filme){
        this.listaDeFilmes.add(filme);
        this.mapaIdFilme.add(filme.id(), filme);
        String title = filme.title().toLowerCase();
        this.mapaFilmePorNome.add(title, filme);

    }
    public void pularLinha(int numeroDeLinhas){
        for (int i = 1; i <= numeroDeLinhas; i++) {
            System.out.println();
        }
    }

    private void buscaPorId(){
        System.out.print("Digite o id do Filme: ");
        Integer id = leitor.obterEntradaAsInt();
        for (Filme filme: listaDeFilmes){
            System.out.println("Pesquisou na Lista: " + id);
            if(id.equals(filme.id())){
                System.out.println("Filme localizado!");
                System.out.println(filme);
                return;
            }
        }
        System.out.println("Nenhum filme localizado para o id: " + id);
    }

    private void buscaPorIdHashMap(){
        System.out.print("Digite o id do Filme: ");
        Integer id = leitor.obterEntradaAsInt();
        Filme filme = this.mapaIdFilme.get(id);
        if(filme != null){
            System.out.println("filme localizado!");
            System.out.println(filme);
        } else {
            System.out.println("Nenhum filme localizado para o id: " + id);
        }
    }

    private void buscaPorTitleHashMap(){
        System.out.print("Digite o titulo do filme: ");
        String title = leitor.obterEntrada().toLowerCase();
        Filme filme = this.mapaFilmePorNome.get(title);
        if(filme != null){
            System.out.println("filmes(s) localizado(s)!");
            System.out.println(filme);

        } else {
            System.out.println("Nenhum filme localizado para o nome: " + title);
        }
    }

    private void listarFilmes(){
        StringBuilder sb = new StringBuilder();

        if (listaDeFilmes.isEmpty()) {
            sb.append("[]");
        } else {
            sb.append("[\n");
            for (Filme funcionario : listaDeFilmes) {
                sb.append("\t").append(funcionario).append(",\n");
            }
            sb.setLength(sb.length() - 2); // Remover a vírgula extra após o último funcionário
            sb.append("\n]");
        }

        System.out.println(sb);
    }

    private boolean escolheuSair(String opcaoDigitada){
        return opcaoDigitada.equals(OPCAO_SAIR);
    }

    private String obterEntradaDoUsuario(EntradaDeDados leitor){
        carregaMenu();
        System.out.print(DIGITE_OPCAO_DESEJADA);
        return leitor.obterEntrada().toLowerCase();
    }

    private Filme construirFilme(Integer id, EntradaDeDados leitor){
        System.out.println("Cadastro de novo filme");
        System.out.print("Informe o title: ");
        String title = leitor.obterEntrada();
        System.out.print("Informe o resumo: ");
        String overview = leitor.obterEntrada();
        System.out.print("Informe a lingua original: ");
        String original_language = leitor.obterEntrada();
        System.out.print("Informe a contagem de votos: ");
        String vote_count = leitor.obterEntrada();
        System.out.print("Informe a media de votos: ");
        String vote_average = leitor.obterEntrada();
        return new Filme(id, title, overview, original_language,vote_count, vote_average );
    }

    private void finalizaApp(){
        System.out.println("Até logo!!");
    }

    private void opcaoInvalida(){
        System.out.println("Opção INVÁLIDA. Tente novamente.");
    }

    private void iniciaApp(){
        carregaNomeApp();
    }

    private void carregaMenu() {
        System.out.println("********  DIGITE A OPÇÃO DESEJADA   ******");
        System.out.println("1 - CADASTRAR FILME");
        System.out.println("2 - LISTAR FILMES");
        System.out.println("3 - CADASTRO EM LOTE (CSV)");
        System.out.println("4 - PESQUISAR POR ID");
        System.out.println("5 - PESQUISAR POR TITULO");
        System.out.println("X - SAIR");
    }

    private void carregaNomeApp(){
        System.out.println("******************************************");
        System.out.println("******* CADASTRO DE FILMES ***************");
        System.out.println("******************************************");
    }

}
