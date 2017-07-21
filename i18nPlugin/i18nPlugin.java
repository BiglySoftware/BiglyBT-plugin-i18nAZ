/*
 * i18nPlugin.java
 *
 * Created on February 22, 2004, 5:43 PM
 */

package i18nPlugin;

import com.biglybt.pif.PluginException;
import com.biglybt.pif.PluginInterface;
import com.biglybt.pif.UnloadablePlugin;
import com.biglybt.pif.ui.UIInstance;
import com.biglybt.pif.ui.UIManagerListener;
import com.biglybt.ui.swt.pif.UISWTInstance;

/**
 * ResourceBundle Comparer/Editor: Plugin into Azureus
 * @author TuxPaper
 */
public class i18nPlugin implements UnloadablePlugin {
	UISWTInstance swtInstance = null;

	View myView = null;
	
	@Override
	public void initialize(final PluginInterface pluginInterface) {
		try {
			pluginInterface.getUIManager().addUIListener(new UIManagerListener() {
				@Override
				public void UIAttached(UIInstance instance) {
					if (instance instanceof UISWTInstance) {
						swtInstance = (UISWTInstance)instance;
						myView =  new View(pluginInterface, swtInstance);
						
						swtInstance.addView(UISWTInstance.VIEW_MAIN, View.VIEWID, myView);
					}
				}
	
				@Override
				public void UIDetached(UIInstance instance) {
					swtInstance = null;
				}
			});
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	@Override
	public void unload() throws PluginException {
		if (swtInstance == null || myView == null)
			return;

		swtInstance.removeViews(UISWTInstance.VIEW_MAIN, View.VIEWID);

		myView = null;
	}
}

