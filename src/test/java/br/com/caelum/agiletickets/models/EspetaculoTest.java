package br.com.caelum.agiletickets.models;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Locale;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Test;

public class EspetaculoTest {

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.isQuantidadeVagas(5));
	}

	@Test
	public void deveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertTrue(ivete.isQuantidadeVagas(6));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoes() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(1));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.isQuantidadeVagas(15));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.isQuantidadeVagasComMinimoPorSessao(5, 3));
	}

	@Test
	public void DeveInformarSeEhPossivelReservarAQuantidadeExataDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(3));
		ivete.getSessoes().add(sessaoComIngressosSobrando(4));

		assertTrue(ivete.isQuantidadeVagasComMinimoPorSessao(10, 3));
	}

	@Test
	public void DeveInformarSeNaoEhPossivelReservarAQuantidadeDeIngressosDentroDeQualquerDasSessoesComUmMinimoPorSessao() {
		Espetaculo ivete = new Espetaculo();

		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));
		ivete.getSessoes().add(sessaoComIngressosSobrando(2));

		assertFalse(ivete.isQuantidadeVagasComMinimoPorSessao(5, 3));
	}

	private Sessao sessaoComIngressosSobrando(int quantidade) {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(quantidade * 2);
		sessao.setIngressosReservados(quantidade);

		return sessao;
	}
	
	@Test
	public void deveCriarUmaSessaoDiaria() {
		Espetaculo espetaculo = new Espetaculo();
		LocalDate dataInicio = new LocalDate(2013, 7, 1);
		LocalDate dataFim = new LocalDate(2013, 7, 1);
		LocalTime horario = new LocalTime(18, 0, 0);
		
		List<Sessao> resultado = espetaculo.criaSessoes(dataInicio, dataFim, horario, Periodicidade.DIARIA);
		
		assertTrue(resultado.size() == 1);
		assertEquals(dataInicio.toString(DateTimeFormat.shortDate().withLocale(new Locale("pt", "BR"))),resultado.get(0).getDia());
	}
	
	@Test
	public void deveCriarUmaSessaoSemanal() {
		Espetaculo espetaculo = new Espetaculo();
		LocalDate dataInicio = new LocalDate(2013, 7, 1);
		LocalDate dataFim = new LocalDate(2013, 7, 7);
		LocalTime horario = new LocalTime(18, 0, 0);
		
		List<Sessao> resultado = espetaculo.criaSessoes(dataInicio, dataFim, horario, Periodicidade.SEMANAL);
		
		assertTrue(resultado.size() == 1);
		assertEquals(dataInicio.toString(DateTimeFormat.shortDate().withLocale(new Locale("pt", "BR"))),resultado.get(0).getDia());
	}
	
	@Test
	public void deveCriarVariasSessoesDiarias() {
		Espetaculo espetaculo = new Espetaculo();
		LocalDate dataInicio = new LocalDate(2013, 7, 1);
		LocalDate dataFim = new LocalDate(2013, 7, 7);
		LocalDate dataMeio = new LocalDate(2013, 7, 4);
		LocalTime horario = new LocalTime(18, 0, 0);
		
		List<Sessao> resultado = espetaculo.criaSessoes(dataInicio, dataFim, horario, Periodicidade.DIARIA);
		
		assertTrue(resultado.size() == 7);
		assertEquals(dataInicio.toString(DateTimeFormat.shortDate().withLocale(new Locale("pt", "BR"))),resultado.get(0).getDia());
		assertEquals(dataMeio.toString(DateTimeFormat.shortDate().withLocale(new Locale("pt", "BR"))),resultado.get(3).getDia());
		assertEquals(dataFim.toString(DateTimeFormat.shortDate().withLocale(new Locale("pt", "BR"))),resultado.get(6).getDia());
	
	}
	
	@Test
	public void deveCriarVariasSessoesSemanais() {
		Espetaculo espetaculo = new Espetaculo();
		LocalDate dataInicio = new LocalDate(2013, 7, 1);
		LocalDate dataMeio = new LocalDate(2013, 7, 8);
		LocalDate dataFim = new LocalDate(2013, 7, 15);
		LocalTime horario = new LocalTime(18, 0, 0);
		
		List<Sessao> resultado = espetaculo.criaSessoes(dataInicio, dataFim, horario, Periodicidade.SEMANAL);
		
		assertTrue(resultado.size() == 3);
		assertEquals(dataInicio.toString(DateTimeFormat.shortDate().withLocale(new Locale("pt", "BR"))),resultado.get(0).getDia());
		assertEquals(dataMeio.toString(DateTimeFormat.shortDate().withLocale(new Locale("pt", "BR"))),resultado.get(1).getDia());
		assertEquals(dataFim.toString(DateTimeFormat.shortDate().withLocale(new Locale("pt", "BR"))),resultado.get(2).getDia());
	}
	
}
