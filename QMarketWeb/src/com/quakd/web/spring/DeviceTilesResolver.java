package com.quakd.web.spring;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.mobile.device.site.SitePreferenceUtils;
import org.springframework.mobile.device.util.ResolverUtils;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

public class DeviceTilesResolver extends TilesViewResolver {
	
	private String normalPrefix = "";

	private String mobilePrefix = "";

	private String tabletPrefix = "";

	private String normalSuffix = "";

	private String mobileSuffix = "";

	private String tabletSuffix = "";

	/**
	 * Set the prefix that gets prepended to view names for normal devices.
	 */
	public void setNormalPrefix(String normalPrefix) {
		this.normalPrefix = (normalPrefix != null ? normalPrefix : "");
	}

	/**
	 * Return the prefix that gets prepended to view names for normal devices
	 */
	protected String getNormalPrefix() {
		return this.normalPrefix;
	}

	/**
	 * Set the prefix that gets prepended to view names for mobile devices.
	 */
	public void setMobilePrefix(String mobilePrefix) {
		this.mobilePrefix = (mobilePrefix != null ? mobilePrefix : "");
	}

	/**
	 * Return the prefix that gets prepended to view names for mobile devices
	 */
	protected String getMobilePrefix() {
		return this.mobilePrefix;
	}

	/**
	 * Set the prefix that gets prepended to view names for tablet devices.
	 */
	public void setTabletPrefix(String tabletPrefix) {
		this.tabletPrefix = (tabletPrefix != null ? tabletPrefix : "");
	}

	/**
	 * Return the prefix that gets prepended to view names for tablet devices
	 */
	protected String getTabletPrefix() {
		return this.tabletPrefix;
	}

	/**
	 * Set the suffix that gets appended to view names for normal devices.
	 */
	public void setNormalSuffix(String normalSuffix) {
		this.normalSuffix = (normalSuffix != null ? normalSuffix : "");
	}

	/**
	 * Return the suffix that gets appended to view names for normal devices
	 */
	protected String getNormalSuffix() {
		return this.normalSuffix;
	}

	/**
	 * Set the suffix that gets appended to view names for mobile devices
	 */
	public void setMobileSuffix(String mobileSuffix) {
		this.mobileSuffix = (mobileSuffix != null ? mobileSuffix : "");
	}

	/**
	 * Return the suffix that gets appended to view names for mobile devices
	 */
	protected String getMobileSuffix() {
		return this.mobileSuffix;
	}

	/**
	 * Set the suffix that gets appended to view names for tablet devices
	 */
	public void setTabletSuffix(String tabletSuffix) {
		this.tabletSuffix = (tabletSuffix != null ? tabletSuffix : "");
	}

	/**
	 * Return the suffix that gets appended to view names for tablet devices
	 */
	protected String getTabletSuffix() {
		return this.tabletSuffix;
	}

	
	@Override
	protected TilesView buildView(String viewName) throws Exception {
		RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
		Assert.isInstanceOf(ServletRequestAttributes.class, attrs);
		HttpServletRequest request = ((ServletRequestAttributes) attrs).getRequest();
		Device device = DeviceUtils.getCurrentDevice(request);
		SitePreference sitePreference = SitePreferenceUtils.getCurrentSitePreference(request);
		String resolvedViewName = viewName;
		if (ResolverUtils.isNormal(device, sitePreference)) {
			resolvedViewName = getNormalPrefix() + viewName + getNormalSuffix();
		} else if (ResolverUtils.isMobile(device, sitePreference)) {
			resolvedViewName = getMobilePrefix() + viewName + getMobileSuffix();
		} else if (ResolverUtils.isTablet(device, sitePreference)) {
			resolvedViewName = getTabletPrefix() + viewName + getTabletSuffix();
		}

		// MOBILE-63 "redirect:/" and "forward:/" can result in the view name containing multiple trailing slashes 
		return super.buildView(stripTrailingSlash(resolvedViewName));
	}

	private String stripTrailingSlash(String viewName) {
		if (viewName.endsWith("//")) {
			return viewName.substring(0, viewName.length() - 1);
		}
		return viewName;
	}

	

}
