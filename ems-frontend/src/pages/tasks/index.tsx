// // import ThemeSwitch from '@/components/theme-switch'
// import { UserNav } from '@/components/user-nav'
// import { Layout, LayoutBody, LayoutHeader } from '@/components/custom/layout'

// export default function Tasks() {
//   return (
//     <div>
//       {/* ===== Top Heading ===== */}
//       <div>
//         {/* <Search /> */}
//         <div className='ml-auto flex items-center space-x-4'>
//           {/* <ThemeSwitch /> */}
//           <UserNav />
//         </div>
//       </div>

//       <div className='flex flex-col' fixedHeight>
//         <div className='mb-2 flex items-center justify-between space-y-2'>
//           <div>
//             <h2 className='text-2xl font-bold tracking-tight'>Welcome back!</h2>
//             <p className='text-muted-foreground'>
//               Here&apos;s a list of your tasks for this month!
//             </p>
//           </div>
//         </div>
//         <div className='-mx-4 flex-1 overflow-auto px-4 py-1 lg:flex-row lg:space-x-12 lg:space-y-0'></div>
//       </div>
//     </div>
//   )
// }
import { UserNav } from '@/components/user-nav'
import { TaskForm } from './taskforms/addtask-form'

const Tasks = () => {
  return (
    <div className='container relative flex h-auto w-full flex-col'>
      <div className=' flex-1 space-y-10 overflow-hidden px-4 py-6 md:px-8'>
        <div className='flex items-center justify-between space-y-2'>
          <h1 className='text-2xl font-bold tracking-tight md:text-3xl'>
            Add new Task Board
          </h1>
          <UserNav color='black' />
        </div>
        <TaskForm />
      </div>
    </div>
  )
}

export default Tasks
