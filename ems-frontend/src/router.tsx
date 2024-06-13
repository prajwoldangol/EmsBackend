import { createBrowserRouter } from 'react-router-dom'
import NotFoundError from './pages/errors/not-found-error'
import ProtectedRoute from './router/Lazyloader.tsx'

const router = createBrowserRouter([
  {
    path: '/',

    lazy: async () => ({
      Component: (await import('./pages/home')).default,
    }),
  },
  // Auth routes

  {
    path: 'add-task',
    lazy: async () => ({
      Component: (await import('./pages/tasks/taskforms/add')).default,
    }),
  },
  {
    path: 'unauthorized',
    lazy: async () => ({
      Component: (await import('./router/unauthorized.tsx')).default,
    }),
  },

  {
    path: 'employee-dashboard',
    // lazy: async () => ({
    //   Component: (await import('./employee')).default,
    // }),
    element: (
      <ProtectedRoute
        componentImport={() => import('./employee')}
        requiredRole={['EMPLOYEE']}
      />
    ),
  },

  {
    path: '/login',
    lazy: async () => ({
      Component: (await import('./pages/auth/logins')).default,
    }),
  },
  {
    path: '/employee-login',
    lazy: async () => ({
      Component: (await import('./pages/auth/employee-login')).default,
    }),
  },
  {
    path: '/register',
    lazy: async () => ({
      Component: (await import('./pages/auth/register')).default,
    }),
  },

  {
    path: '/reset-password',
    lazy: async () => ({
      Component: (await import('./pages/auth/forgot-password')).default,
    }),
  },
  {
    path: '/otp',
    lazy: async () => ({
      Component: (await import('./pages/auth/otp')).default,
    }),
  },
  {
    path: 'success',
    lazy: async () => ({
      Component: (await import('./router/success')).default,
    }),
  },

  // Main routes
  {
    path: '/dashboard',
    // lazy: async () => {
    //   const AppShell = await import('./components/app-shell')
    //   return { Component: AppShell.default }
    // },
    element: (
      <ProtectedRoute
        componentImport={() => import('./components/app-shell')}
        requiredRole={['EMPLOYER_BASIC', 'EMPLOYER_STANDARD', 'EMPLOYER_PLUS']}
      />
    ),
    // errorElement: <GeneralError />,
    children: [
      {
        index: true,
        lazy: async () => ({
          Component: (await import('./pages/dashboard')).default,
        }),
      },
      {
        path: 'allEmployees',
        lazy: async () => ({
          Component: (await import('./pages/employee-pages/index.tsx')).default,
        }),
      },
      {
        path: 'addEmployee',
        lazy: async () => ({
          Component: (await import('./pages/employee-pages/employee-add.tsx'))
            .default,
        }),
      },
      {
        path: 'addDepartment',
        lazy: async () => ({
          Component: (
            await import('./pages/dashboard/department/department-add.tsx')
          ).default,
        }),
      },
      {
        path: 'allDepartments',
        lazy: async () => ({
          Component: (
            await import('./pages/dashboard/department/department-table')
          ).default,
        }),
      },
      {
        path: 'schedule',
        lazy: async () => ({
          Component: (await import('./pages/schedule/schedule.tsx')).default,
        }),
      },

      {
        path: 'tasks',
        lazy: async () => ({
          Component: (await import('@/pages/tasks')).default,
        }),
      },

      {
        path: 'settings',
        lazy: async () => ({
          Component: (await import('./pages/settings')).default,
        }),
        errorElement: <NotFoundError />,
        // children: [
        //   {
        //     index: true,
        //     lazy: async () => ({
        //       Component: (await import('./pages/settings')).default,
        //     }),
        //   },
        // ],
      },
    ],
  },

  // Error routes

  { path: '/404', Component: NotFoundError },

  // Fallback 404 route
  { path: '*', Component: NotFoundError },
])

export default router
