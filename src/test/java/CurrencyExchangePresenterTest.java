import currencyExchange.CurrencyExchangeFrame;
import currencyExchange.CurrencyExchangePresenter;
import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import currencyExchange.json.CurrencyExchange;
import currencyExchange.json.CurrencyExchangeService;
import currencyExchange.json.Symbol;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Provider;
import java.util.Map;

import static org.mockito.Mockito.*;

class CurrencyExchangePresenterTest {
    @BeforeAll
    static void beforeAllTests() {
        // this will run one time before all tests in this class
        RxJavaPlugins.setIoSchedulerHandler((scheduler) -> Schedulers.trampoline());
        RxJavaPlugins.setNewThreadSchedulerHandler((scheduler) -> Schedulers.trampoline());
    }

    @BeforeEach
    public void beforeEachTest() {
        // this will get run before each test
    }

    @Test
    void loadResultFromQuery() {
        //given
        CurrencyExchangeFrame view = mock(CurrencyExchangeFrame.class);
        CurrencyExchangeService model = mock(CurrencyExchangeService.class);
        Provider<CurrencyExchangeFrame> viewProvider = () -> view;
        CurrencyExchangePresenter presenter = new CurrencyExchangePresenter(viewProvider, model);
        CurrencyExchange currencyExchange = mock(CurrencyExchange.class);
        doReturn(100.0).when(currencyExchange).getResult();
        doReturn(Single.just(currencyExchange)).when(model).getCurrencyExchange(100, "USD", "ILS");

        //when
        presenter.loadResultFromQuery(100, "USD", "ILS");

        //then
        verify(view).setResultLabel("100.0");
    }


    @Test
    void loadSymbolsChoices() {
        //given
        CurrencyExchangeFrame view = mock(CurrencyExchangeFrame.class);
        CurrencyExchangeService model = mock(CurrencyExchangeService.class);
        Provider<CurrencyExchangeFrame> viewProvider = () -> view;
        CurrencyExchangePresenter presenter = new CurrencyExchangePresenter(viewProvider, model);
        CurrencyExchange currencyExchange = mock(CurrencyExchange.class);
        Map<String, Symbol> symbols = Map.of();

        doReturn(symbols).when(currencyExchange).getSymbols();
        doReturn(Single.just(currencyExchange)).when(model).getCurrencySymbols();

        //when
        presenter.loadSymbolsChoices();

        //then
        verify(view).setSymbolsChoices(symbols);
    }
}