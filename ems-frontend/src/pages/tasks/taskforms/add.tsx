// import { Layout, LayoutHeader } from '@/components/custom/layout'
import Sidebar2 from '@/components/sidebar'
// import ThemeSwitch from '@/components/theme-switch'
import { UserNav } from '@/components/user-nav'
// import useIsCollapsed from '@/hooks/use-is-collapsed'
// import { Separator } from '@radix-ui/react-separator'
import { TaskForm } from './addtask-form'

const AddTask = () => {
  // const [isCollapsed, setIsCollapsed] = useIsCollapsed()
  return (
    <div className='relative h-full overflow-hidden bg-background'>
      <Sidebar2 className='' />
      <main
        id='content'
        className={`h-full overflow-x-hidden pt-16 transition-[margin] md:ml-64 md:overflow-y-hidden md:pt-0`}
      >
        <div className='container'>
          <div>
            <div className='ml-auto flex items-center space-x-4'>
              {/* <ThemeSwitch /> */}
              <UserNav />
            </div>
          </div>
          <div className='w-full space-y-6 p-10 lg:w-2/4'>
            <div>
              <h3 className='text-lg font-medium'>Add New Task</h3>
              <p className='text-sm text-muted-foreground'>
                Add Tasks and set the details.
              </p>
            </div>
            {/* <Separator /> */}
            <TaskForm />
          </div>
        </div>
      </main>
    </div>
  )
}

export default AddTask
