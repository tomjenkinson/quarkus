package io.quarkus.narayana.jta.runtime;

import java.util.Properties;

import org.jboss.logging.Logger;

import com.arjuna.ats.arjuna.common.CoreEnvironmentBeanException;
import com.arjuna.ats.arjuna.common.arjPropertyManager;
import com.arjuna.ats.arjuna.coordinator.TxControl;
import com.arjuna.common.util.propertyservice.PropertiesFactory;

import io.quarkus.runtime.annotations.Recorder;

@Recorder
public class NarayanaJtaRecorder {

    private static Properties defaultProperties;

    private static final Logger log = Logger.getLogger(NarayanaJtaRecorder.class);

    public void setNodeName(final TransactionManagerConfiguration transactions) {

        try {
            arjPropertyManager.getCoreEnvironmentBean().setNodeIdentifier(transactions.nodeName);
            TxControl.setXANodeName(transactions.xaNodeName.orElse(transactions.nodeName));
        } catch (CoreEnvironmentBeanException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultProperties(Properties properties) {
        PropertiesFactory.setDelegatePropertiesFactory(new QuarkusPropertiesFactory(properties));
        defaultProperties = properties;
    }

    public void setDefaultTimeout(TransactionManagerConfiguration transactions) {
        transactions.defaultTransactionTimeout.ifPresent(defaultTimeout -> {
            TxControl.setDefaultTimeout((int) defaultTimeout.getSeconds());
        });
    }

    public static Properties getDefaultProperties() {
        return defaultProperties;
    }

    public void setTransactionStatusManagerEnabled(TransactionManagerConfiguration transactions) {
        arjPropertyManager.getCoordinatorEnvironmentBean()
                .setTransactionStatusManagerEnable(transactions.enableTransactionStatusManager);
    }
}
