// import { RootState } from '@/store/store'
// import React, { useEffect, useState } from 'react'
// import { useSelector } from 'react-redux'
// type ActionsState = {
//   [key: number]: boolean
// }
// const DataTable = () => {
//   const [actions, setActions] = useState<ActionsState>({})

//   const showAction = (index: number) => {
//     setActions((prevActions) => ({
//       ...prevActions,
//       [index]: !prevActions[index],
//     }))
//   }

//   const handleClickOutside = (event: MouseEvent) => {
//     Object.keys(actions).forEach((index) => {
//       const numIndex = Number(index)
//       const actionElement = document.getElementById(`actionRef-${numIndex}`)

//       if (
//         actions[numIndex] &&
//         actionElement &&
//         !actionElement.contains(event.target as Node)
//       ) {
//         setActions((prevActions) => ({
//           ...prevActions,
//           [numIndex]: false,
//         }))
//       }
//     })
//   }

//   useEffect(() => {
//     document.body.addEventListener('click', handleClickOutside)

//     return () => {
//       document.body.removeEventListener('click', handleClickOutside)
//     }
//   }, [actions])
//   const employees =  useSelector((state: RootState) => state.users.userData.emsEmployerEmpDtos)
//   const rows = [
//     {
//       company: 'Circooles',
//       url: 'getcirooles.com',
//       status: 'Churned',
//       description: 'Design software',
//       time: '9 AM',
//     },
//     {
//       company: 'Circooles',
//       url: 'getcirooles.com',
//       status: 'Churned',
//       description: 'Design software',
//       time: '9 AM',
//     },
//     {
//       company: 'Circooles',
//       url: 'getcirooles.com',
//       status: 'Churned',
//       description: 'Design software',
//       time: '9 AM',
//     },
//     // Add other rows here
//   ]

//   return (
//     <section className='container mx-auto px-4'>
//       <div className='mt-6 flex flex-col'>
//         <div className='-mx-4 -my-2 overflow-x-auto sm:-mx-6 lg:-mx-8'>
//           <div className='inline-block min-w-full py-2 align-middle md:px-6 lg:px-8'>
//             <div className='overflow-hidden border border-gray-200 dark:border-gray-700 md:rounded-lg'>
//               <table className='min-w-full divide-y divide-gray-200 dark:divide-gray-700'>
//                 <thead className='bg-gray-50 dark:bg-gray-800'>
//                   <tr>
//                     <th
//                       scope='col'
//                       className='px-4 py-3.5 text-left text-sm font-normal text-gray-500 dark:text-gray-400 rtl:text-right'
//                     >
//                       <button className='flex items-center gap-x-3 focus:outline-none'>
//                         <span>Employee Number</span>
//                       </button>
//                     </th>
//                     <th
//                       scope='col'
//                       className='px-12 py-3.5 text-left text-sm font-normal text-gray-500 dark:text-gray-400 rtl:text-right'
//                     >
//                       Department
//                     </th>
//                     <th
//                       scope='col'
//                       className='px-4 py-3.5 text-left text-sm font-normal text-gray-500 dark:text-gray-400 rtl:text-right'
//                     >
//                       Status
//                     </th>
//                     <th
//                       scope='col'
//                       className='px-4 py-3.5 text-left text-sm font-normal text-gray-500 dark:text-gray-400 rtl:text-right'
//                     >
//                       Active Since
//                     </th>
//                     <th scope='col' className='relative px-4 py-3.5'>
//                       <span className='sr-only'>Edit</span>
//                     </th>
//                   </tr>
//                 </thead>
//                 <tbody className='divide-y divide-gray-200 bg-white dark:divide-gray-700 dark:bg-gray-900'>
//                   {rows.map((row, index) => (
//                     <tr key={index}>
//                       <td className='whitespace-nowrap px-4 py-4 text-sm font-medium'>
//                         <div>
//                           <h2 className='font-medium text-gray-800 dark:text-white '>
//                             {row.company}
//                           </h2>
//                           <p className='text-sm font-normal text-gray-600 dark:text-gray-400'>
//                             {row.url}
//                           </p>
//                         </div>
//                       </td>
//                       <td className='whitespace-nowrap px-12 py-4 text-sm font-medium'>
//                         <div className='inline gap-x-2 rounded-full bg-gray-100 px-3 py-1 text-sm font-normal text-gray-500 dark:bg-gray-800 dark:text-gray-400'>
//                           {row.status}
//                         </div>
//                       </td>
//                       <td className='whitespace-nowrap px-4 py-4 text-sm'>
//                         <div>
//                           <h4 className='text-gray-700 dark:text-gray-200'>
//                             {row.description}
//                           </h4>
//                           <p className='text-gray-500 dark:text-gray-400'>
//                             {row.description}
//                           </p>
//                         </div>
//                       </td>
//                       <td className='whitespace-nowrap px-4 py-4 text-sm'>
//                         <p className='text-gray-500 dark:text-gray-400'>
//                           {row.time}
//                         </p>
//                       </td>
//                       <td
//                         id={`actionRef-${index}`}
//                         className='relative whitespace-nowrap px-4 py-4 text-sm'
//                       >
//                         <button
//                           onClick={() => showAction(index)}
//                           className='relative rounded-lg px-1 py-1 text-gray-500 transition-colors duration-200 hover:bg-gray-100 dark:text-gray-300'
//                         >
//                           <svg
//                             xmlns='http://www.w3.org/2000/svg'
//                             fill='none'
//                             viewBox='0 0 24 24'
//                             strokeWidth='1.5'
//                             stroke='currentColor'
//                             className='h-6 w-6'
//                           >
//                             <path
//                               strokeLinecap='round'
//                               strokeLinejoin='round'
//                               d='M12 6.75a.75.75 0 110-1.5.75.75 0 010 1.5zM12 12.75a.75.75 0 110-1.5.75.75 0 010 1.5zM12 18.75a.75.75 0 110-1.5.75.75 0 010 1.5z'
//                             />
//                           </svg>
//                           <div
//                             className={`${
//                               actions[index] ? 'block' : 'hidden'
//                             } absolute -top-11 right-6  z-20 w-max overflow-hidden rounded-md border bg-white transition-all duration-500 ease-in-out`}
//                             style={{
//                               opacity: actions[index] ? 1 : 0,
//                             }}
//                           >
//                             <div className='relative'>
//                               <div className='absolute right-4 top-0 -mt-2 h-0 w-0 rotate-45 transform border-l-2 border-r-2 border-t-2 border-[#1F2937]' />
//                               <a
//                                 href='#'
//                                 className='block px-4 py-2 text-lg text-gray-900 hover:text-yellow-900'
//                               >
//                                 Suspend
//                               </a>
//                               <a
//                                 href='#'
//                                 className='block px-4 py-2 text-lg text-gray-900 hover:text-yellow-900'
//                               >
//                                 Delete
//                               </a>
//                             </div>
//                           </div>
//                         </button>
//                       </td>
//                     </tr>
//                   ))}
//                 </tbody>
//               </table>
//             </div>
//           </div>
//         </div>
//       </div>
//     </section>
//   )
// }

// export default DataTable