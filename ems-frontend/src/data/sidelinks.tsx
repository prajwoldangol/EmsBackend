import {
  IconChecklist,
  IconLayoutDashboard,
  IconSettings,
  IconUsers,
} from '@tabler/icons-react'

export interface NavLink {
  title: string
  label?: string
  href: string
  icon?: JSX.Element
}

export interface SideLink extends NavLink {
  sub?: NavLink[]
}

export const sidelinks: SideLink[] = [
  {
    title: 'Dashboard',
    label: '',
    href: '/dashboard',
    icon: <IconLayoutDashboard size={18} />,
  },

  {
    title: 'Task Board',
    label: '',
    href: '/dashboard/tasks',
    icon: <IconChecklist size={18} />,
  },
  {
    title: 'Schedules',
    label: '',
    href: '/dashboard/schedule',
    icon: <IconChecklist size={18} />,
  },
  {
    title: 'Departments',
    label: '',
    href: '/dashboard/addDepartment',
    icon: <IconChecklist size={18} />,
  },
  {
    title: 'All Departments',
    label: '',
    href: '/dashboard/allDepartments',
    icon: <IconChecklist size={18} />,
  },
  {
    title: 'Settings',
    label: '',
    href: '',
    icon: <IconSettings size={18} />,
    sub: [
      {
        title: 'Employees',
        label: '',
        href: '/sign-in',
        icon: <IconUsers size={18} />,
      },

      {
        title: 'Schedules',
        label: '',
        href: '/otp',
      },
    ],
  },
  {
    title: 'All Employees',
    label: '',
    href: '/dashboard/allEmployees',
    icon: <IconUsers size={18} />,
  },
  {
    title: 'Add Employees',
    label: '',
    href: '/dashboard/addEmployee',
    icon: <IconUsers size={18} />,
  },

  {
    title: 'Settings',
    label: '',
    href: '/dashboard/settings',
    icon: <IconSettings size={18} />,
  },
]
