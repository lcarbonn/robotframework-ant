#  Copyright 2008-2011 Nokia Siemens Networks Oyj
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.

from runonfailure import RunOnFailure


class Browser(RunOnFailure):
    """Contains keywords for doing browser actions."""

    def go_to(self, url):
        """Navigates the active browser instance to the provided URL."""
        self._info("Opening url '%s'" % url)
        self._selenium.open(url, ignoreResponseCode=True)

    def wait_until_page_loaded(self, timeout=None):
        """Waits for a page load to happen.

        This keyword can be used after performing an action that causes a page
        load to ensure that following keywords see the page fully loaded.

        `timeout` is the time to wait for the page load to happen, after which
        this keyword fails. If `timeout` is not provided, the value given in
        `importing` or using keyword `Set Timeout` is used.

        Many of the keywords that cause a page load take an optional argument
        `dont_wait` that can be also used to wait/not wait page load. See
        `introduction` for more details.

        This keyword was added in SeleniumLibrary 2.5.
        """
        timeout = self._get_timeout(timeout)
        self._selenium.wait_for_page_to_load(timeout * 1000)

    def go_back(self, dont_wait=''):
        """Simulates the user clicking the "back" button on their browser.

        See `introduction` for details about locating elements and about meaning
        of `dont_wait` argument."""
        self._selenium.go_back()
        if not dont_wait:
            self.wait_until_page_loaded()

    def maximize_browser_window(self):
        """Maximizes current browser window."""
        self._selenium.window_maximize()

    def get_window_names(self):
        """Returns and logs names of all windows known to the browser."""
        return self._log_list(self._selenium.get_all_window_names(), 'name')

    def get_window_titles(self):
        """Returns and logs titles of all windows known to the browser."""
        return self._log_list(self._selenium.get_all_window_titles(), 'title')

    def get_window_identifiers(self):
        """Returns and logs id attributes of all windows known to the browser."""
        return self._log_list(self._selenium.get_all_window_ids(), 'identifier')

    def select_window(self, locator='main'):
        """Selects the window found with `locator` as the context of actions.

        If the window is found, all subsequent commands use that window, until
        this keyword is used again. If the window is not found, this keyword fails.

        `locator` may be either the title of the window or the name of the window
        in the JavaScript code that creates it. If multiple windows with
        same identifier are found, the first one is selected.

        Special locator `main` (default) can be used to select the main window.

        Example:
        | Click Link | popup_link | don't wait | # opens new window |
        | Select Window | popupName |
        | Title Should Be | Popup Title |
        | Select Window |  | | # Chooses the main window again |

        *NOTE:* Selecting windows opened by links with target `_blank` does
        not seem to work on Internet Explorer.
        """
        if locator.lower() == 'main':
            locator = 'null'
        self._selenium.select_window(locator)

    def close_window(self):
        """Closes currently opened pop-up window."""
        self._selenium.close()

    def get_location(self):
        """Returns the current location."""
        return self._selenium.get_location()

    def get_cookies(self):
        """Returns all cookies of the current page."""
        return self._selenium.get_cookie()

    def get_cookie_value(self, name):
        """Returns value of cookie found with `name`.

        If no cookie is found with `name`, this keyword fails.
        """
        return self._selenium.get_cookie_by_name(name)

    def delete_cookie(self, name, options=''):
        """Deletes cookie matching `name` and `options`.

        If the cookie is not found, nothing happens.

        `options` is the options for the cookie as a string. Currently
        supported options include `path`, `domain` and `recurse.` Format for
        `options` is `path=/path/, domain=.foo.com, recurse=true`. The order of
        options is irrelevant. Note that specifying a domain that is not a
        subset of the current domain will usually fail. Setting `recurse=true`
        will cause this keyword to search all sub-domains of current domain
        with all paths that are subset of current path. This can take a long
        time.
        """
        self._selenium.delete_cookie(name, options)

    def delete_all_cookies(self):
        """Deletes all cookies by calling `Delete Cookie` repeatedly."""
        self._selenium.delete_all_visible_cookies()
