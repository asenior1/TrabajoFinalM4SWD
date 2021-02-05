package com.devops.dxc.devops;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.devops.dxc.devops.model.Util;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import static java.time.Duration.ofSeconds;
import org.junit.jupiter.api.DisplayName;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
class DevopsApplicationTests {

	private static Util UTIL = null;
	private final String CONSULTARUT = "8388316-2";
	private final List<String> AFILIADOS = Arrays.asList("13706546-0", "13527931-5", "8388316-2");
	private final List<String> DEUDORES = Arrays.asList("11013725-7", "13706546-0", "13527931-5", "8388316-2");

	@Test
	@DisplayName("NO EXCEDE TIMEOUT CALCULO UF API")
	public void whenAssertingTimeout_thenNotExceeded() {
		assertTimeout(ofSeconds(3), () -> {
			UTIL.getUf();
		});
	}

	@Test
	@DisplayName("¿TENGO SALDO?")
	public void whenAssertingTengoSaldo() {
		assertEquals(9000000, UTIL.getSaldo(10000000, 1000000));
	}

	@Test
	@DisplayName("¿TENGO QUE PAGAR IMPUESTOS?")
	public void whenAssertingImpuestos() {
		assertEquals(320000, UTIL.getImpuesto(2000000, 4000000));
	}

	@Test
	@DisplayName("RETIRO FORZOSO POR DEUDAS DE PENSIONES DE ALIMENTOS")
	public void whenAssertingPesionAlimentos() {
		assertThat(DEUDORES, hasItems(CONSULTARUT));
	}

	@Test
	@DisplayName("¿PUEDO RETIRAR TODOS MIS AHORROS?")
	public void whenAssertingRetirarTodo() {
		assertEquals(900000, UTIL.getDxc(900000, 600000));
	}

	@Test
	@DisplayName("¿PUEDO REALIZAR OTRO RETIRO DEL 10% ?")
	public void whenAssertingRealizarOtroRetiro() {
		assertEquals(900000, UTIL.getDxc(900000, 600000));
		assertThat(AFILIADOS, hasItems(CONSULTARUT));

	}

	@Test
	@DisplayName("MONTO MAXIMO DEL 10%")
	public void whenAssertingMontoMaximo() {
		assertTrue(UTIL.getDxc(100000000, 3000000) == (150 * UTIL.getUf()), "monto maximo permitido");

	}

}
