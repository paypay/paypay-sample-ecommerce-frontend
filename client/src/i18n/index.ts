import Vue from 'vue'
import VueI18n from 'vue-i18n'

Vue.use(VueI18n)

function loadLocaleMessages () {
  const locales = require.context('./locale', true, /[A-Za-z0-9-_,\s]+\.json$/i)
  const messages: {[k: string]: any} = {}

  locales.keys().forEach(key => {
    const matched = key.match(/([A-Za-z0-9-_]+)\./i)
    if (matched && matched.length > 1) {
      messages[matched[1]] = Object.assign({}, messages[matched[1]] || {}, locales(key))
    }
  })
  
  return messages
}

export default new VueI18n({
  locale: process.env.APP_I18N_LOCALE || 'ja',
  fallbackLocale: process.env.APP_I18N_FALLBACK_LOCALE || 'ja',
  messages: loadLocaleMessages()
})
