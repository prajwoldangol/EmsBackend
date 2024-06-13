import React from 'react'
import ReactDOM from 'react-dom/client'
import { RouterProvider } from 'react-router-dom'
// import { Toaster } from '@/components/ui/toaster'
// import { ThemeProvider } from '@/components/theme-provider'
import router from '@/router'
import { Provider } from 'react-redux'
import store, { persistor } from './store/store'
import { PersistGate } from 'redux-persist/integration/react'
import '@/index.css'

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    {/* <ThemeProvider defaultTheme='dark' storageKey='vite-ui-theme'> */}
    <Provider store={store}>
      <PersistGate persistor={persistor}>
        <RouterProvider router={router} />
      </PersistGate>
    </Provider>
    {/* <Toaster /> */}
    {/* </ThemeProvider> */}
  </React.StrictMode>
)
