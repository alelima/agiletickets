package br.com.caelum.agiletickets.models;

import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

@Entity
public class Espetaculo {

	@Id
	@GeneratedValue
	private Long id;

	private String nome;

	private String descricao;

	@Enumerated(EnumType.STRING)
	private TipoDeEspetaculo tipo;

	@OneToMany(mappedBy="espetaculo")
	private List<Sessao> sessoes = newArrayList();

	@ManyToOne
	private Estabelecimento estabelecimento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoDeEspetaculo getTipo() {
		return tipo;
	}

	public void setTipo(TipoDeEspetaculo tipo) {
		this.tipo = tipo;
	}

	public List<Sessao> getSessoes() {
		return sessoes;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	/**
      * Esse metodo eh responsavel por criar sessoes para
      * o respectivo espetaculo, dado o intervalo de inicio e fim,
      * mais a periodicidade.
      * 
      * O algoritmo funciona da seguinte forma:
      * - Caso a data de inicio seja 01/01/2010, a data de fim seja 03/01/2010,
      * e a periodicidade seja DIARIA, o algoritmo cria 3 sessoes, uma 
      * para cada dia: 01/01, 02/01 e 03/01.
      * 
      * - Caso a data de inicio seja 01/01/2010, a data fim seja 31/01/2010,
      * e a periodicidade seja SEMANAL, o algoritmo cria 5 sessoes, uma
      * a cada 7 dias: 01/01, 08/01, 15/01, 22/01 e 29/01.
      * 
      * Repare que a data da primeira sessao é sempre a data inicial.
      */
	public List<Sessao> criaSessoes(LocalDate inicio, LocalDate fim, LocalTime horario, Periodicidade periodicidade) {
		// ALUNO: Não apague esse metodo. Esse sim será usado no futuro! ;)

		List<Sessao> sessoes = new ArrayList<Sessao>();
		int totalSessoes = this.calcularTotalSessoesDeAcodoComAPeriodicidade(inicio, fim, periodicidade);
			
		for (int i = 0; i < totalSessoes; i++) {
			preencheSessoes(sessoes, inicio, i, horario, periodicidade);
		}		
		return sessoes;
	}
	
	private void preencheSessoes(List<Sessao> sessoes, LocalDate inicio, int valorAcrescido, LocalTime horario, Periodicidade periodicidade) {
		Sessao sessao = new Sessao();
		sessao.setInicio(retornaDataAcrescidaDeAcordoComAPeriodicidade(inicio, valorAcrescido, periodicidade)
				.toDateTime(horario));
		sessoes.add(sessao);
	}
	
	private int calcularTotalSessoesDeAcodoComAPeriodicidade(LocalDate inicio, LocalDate fim, Periodicidade periodicidade) {
		int totalSessoes = 0;
		if(periodicidade.equals(Periodicidade.DIARIA)) {
			totalSessoes = Days.daysBetween(inicio, fim).getDays() + 1;
		} else if(periodicidade.equals(Periodicidade.SEMANAL)) {
			totalSessoes = (Days.daysBetween(inicio, fim).getDays())/7 + 1;
		}
		return totalSessoes;
	}
	
	private LocalDate retornaDataAcrescidaDeAcordoComAPeriodicidade(LocalDate data, int valorAcrescido, Periodicidade periodicidade) {
		LocalDate novaData;
		if(periodicidade.equals(Periodicidade.DIARIA)) {
			novaData = data.plusDays(valorAcrescido);
		} else {
			novaData = data.plusWeeks(valorAcrescido);
		}
		return novaData;
	}
	
	public boolean isQuantidadeVagasComMinimoPorSessao(int quantidade, int minimo)    {
        // ALUNO: Não apague esse metodo. Esse sim será usado no futuro! ;)
        int totalDisponivel = 0;
        boolean vagas = false;

        for (Sessao s : sessoes)
        {
            if (s.getIngressosDisponiveis() < minimo) {
            	return vagas;
            }
            totalDisponivel += s.getIngressosDisponiveis();
        }

        if (totalDisponivel >= quantidade) {
        	vagas = true;
        } 
        
        return vagas;
    }

    public boolean isQuantidadeVagas(int quantidade) {
        return this.isQuantidadeVagasComMinimoPorSessao(quantidade, 0);
    }

}
