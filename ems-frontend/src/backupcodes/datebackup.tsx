// import React, { useState, useRef, useEffect, forwardRef } from 'react'
// import { DayPicker, DateRange, Matcher } from 'react-day-picker'
// import { default as defaultStyles } from 'react-day-picker/dist/style.module.css'
// import { format } from 'date-fns'

// type Mode = 'single' | 'range'

// interface DateInputProps {
//   mode: Mode
//   selectedDate?: string
//   selectedRange?: { from: string | undefined; to: string | undefined }
//   onDateChange?: (date: string | undefined) => void
//   onRangeChange?: (
//     range: { from: string | undefined; to: string | undefined } | undefined
//   ) => void
//   disabledDays?: Matcher
//   disabled?: boolean
// }

// const DateInput2 = forwardRef<HTMLInputElement, DateInputProps>(
//   (
//     {
//       mode,
//       selectedDate,
//       selectedRange,
//       onDateChange,
//       onRangeChange,
//       disabledDays,
//       disabled,
//     },
//     ref
//   ) => {
//     const [isPickerOpen, setIsPickerOpen] = useState<boolean>(false)
//     const inputRef = useRef<HTMLDivElement>(null)

//     const handleInputFocus = () => {
//       setIsPickerOpen(true)
//     }

//     const handleClickOutside = (event: MouseEvent) => {
//       if (
//         inputRef.current &&
//         !inputRef.current.contains(event.target as Node)
//       ) {
//         setIsPickerOpen(false)
//       }
//     }

//     useEffect(() => {
//       document.addEventListener('mousedown', handleClickOutside)
//       return () => {
//         document.removeEventListener('mousedown', handleClickOutside)
//       }
//     }, [])

//     const formatDate = (date: string | undefined): string => {
//       return date ? format(new Date(date), 'yyyy-MM-dd') : ''
//     }

//     const formatRange = (
//       range: { from: string | undefined; to: string | undefined } | undefined
//     ): string => {
//       if (!range) return ''
//       const { from, to } = range
//       if (from && to) {
//         return `${format(new Date(from), 'yyyy-MM-dd')} - ${format(new Date(to), 'yyyy-MM-dd')}`
//       }
//       return from ? format(new Date(from), 'yyyy-MM-dd') : ''
//     }
//     const handleSingleSelect = (date: Date | undefined) => {
//       if (onDateChange) onDateChange(date ? date.toISOString() : undefined)
//       setIsPickerOpen(false)
//     }

//     const handleRangeSelect = (range: DateRange | undefined) => {
//       if (onRangeChange) {
//         const formattedRange = range
//           ? {
//               from: range.from ? range.from.toISOString() : undefined,
//               to: range.to ? range.to.toISOString() : undefined,
//             }
//           : undefined
//         onRangeChange(formattedRange)
//       }
//       setIsPickerOpen(false)
//     }
//     // const handleSelect =
//     //   mode === 'single'
//     //     ? (date: Date | undefined) => {
//     //         if (onDateChange)
//     //           onDateChange(date ? date.toISOString() : undefined)
//     //         setIsPickerOpen(false)
//     //       }
//     //     : (range: DateRange | undefined) => {
//     //         if (onRangeChange) {
//     //           const formattedRange = range
//     //             ? {
//     //                 from: range.from ? range.from.toISOString() : undefined,
//     //                 to: range.to ? range.to.toISOString() : undefined,
//     //               }
//     //             : undefined
//     //           onRangeChange(formattedRange)
//     //         }
//     //         setIsPickerOpen(false)
//     //       }

//     return (
//       <div ref={inputRef} style={{ position: 'relative' }}>
//         <input
//           type='text'
//           value={
//             mode === 'single'
//               ? formatDate(selectedDate)
//               : formatRange(selectedRange)
//           }
//           onFocus={handleInputFocus}
//           readOnly
//           placeholder={
//             mode === 'single' ? 'Select a date' : 'Select a date range'
//           }
//           className={`my-2 flex h-9 rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:cursor-not-allowed disabled:opacity-50 ${mode === 'single' ? 'w-3/4' : 'w-1/2'}`}
//           disabled={disabled}
//           required={true}
//           ref={ref}
//         />
//         {isPickerOpen && (
//           <div style={{ position: 'absolute', zIndex: 1000 }}>
//             <DayPicker
//               mode={mode}
//               selected={
//                 mode === 'single'
//                   ? selectedDate
//                     ? new Date(selectedDate)
//                     : undefined
//                   : selectedRange && {
//                       from: selectedRange.from
//                         ? new Date(selectedRange.from)
//                         : undefined,
//                       to: selectedRange.to
//                         ? new Date(selectedRange.to)
//                         : undefined,
//                     }
//               }
//               onSelect={
//                 mode === 'single' ? handleSingleSelect : handleRangeSelect
//               }
//               disabled={disabledDays}
//               classNames={defaultStyles}
//             />
//           </div>
//         )}
//       </div>
//     )
//   }
// )

// export default DateInput2

// import React, { useState, useRef, useEffect } from 'react'
// import { DayPicker, DateRange, Modifier, Matcher } from 'react-day-picker'
// import { default as defaultStyles } from 'react-day-picker/dist/style.module.css'
// import { format } from 'date-fns'

// type Mode = 'single' | 'range'

// interface DateInputProps {
//   mode: Mode | any
//   selectedDate?: string
//   selectedRange?: { from: string | undefined; to: string | undefined }
//   onDateChange?: (date: string | undefined) => void
//   onRangeChange?: (
//     range: { from: string | undefined; to: string | undefined } | undefined
//   ) => void
//   disabledDays?: Matcher
//   disabled?: boolean
// }

// const DateInput2: React.FC<DateInputProps> = ({
//   mode,
//   selectedDate,
//   selectedRange,
//   onDateChange,
//   onRangeChange,
//   disabledDays,
//   disabled,
// }) => {
//   const [isPickerOpen, setIsPickerOpen] = useState<boolean>(false)
//   const inputRef = useRef<HTMLDivElement>(null)

//   const handleInputFocus = () => {
//     setIsPickerOpen(true)
//   }

//   const handleClickOutside = (event: MouseEvent) => {
//     if (inputRef.current && !inputRef.current.contains(event.target as Node)) {
//       setIsPickerOpen(false)
//     }
//   }

//   useEffect(() => {
//     document.addEventListener('mousedown', handleClickOutside)
//     return () => {
//       document.removeEventListener('mousedown', handleClickOutside)
//     }
//   }, [])

//   const formatDate = (date: string | undefined): string => {
//     return date ? format(new Date(date), 'yyyy-MM-dd') : ''
//   }

//   const formatRange = (
//     range: { from: string | undefined; to: string | undefined } | undefined
//   ): string => {
//     if (!range) return ''
//     const { from, to } = range
//     if (from && to) {
//       return `${format(new Date(from), 'yyyy-MM-dd')} - ${format(new Date(to), 'yyyy-MM-dd')}`
//     }
//     return from ? format(new Date(from), 'yyyy-MM-dd') : ''
//   }

//   // Define the type for onSelect prop
//   const handleSelect: any =
//     mode === 'single'
//       ? (date: Date | undefined) => {
//           if (onDateChange) onDateChange(date ? date.toISOString() : undefined)
//           setIsPickerOpen(false)
//         }
//       : (range: DateRange | undefined) => {
//           if (onRangeChange) {
//             const formattedRange = range
//               ? {
//                   from: range.from ? range.from.toISOString() : undefined,
//                   to: range.to ? range.to.toISOString() : undefined,
//                 }
//               : undefined
//             onRangeChange(formattedRange)
//           }
//           setIsPickerOpen(false)
//         }
//   // Function to disable dates before today
//   // Disable dates before today
//   // const disabledDays = {
//   //   before: new Date(),
//   // }
//   // const disabledDays = (date: Date): boolean => {
//   //   if (selectedRange && mode === 'single') {
//   //     const from = selectedRange.from ? new Date(selectedRange.from) : null
//   //     const to = selectedRange.to ? new Date(selectedRange.to) : null
//   //     if (from && to) {
//   //       return !(
//   //         date >= from.setHours(0, 0, 0, 0) &&
//   //         date <= to.setHours(23, 59, 59, 999)
//   //       )
//   //     }
//   //   }
//   //   return false
//   // }
//   // if (mode === 'single' && selectedRange) {
//   //   const { from, to } = selectedRange
//   //   if (from && to) {
//   //     disabledDays.before = new Date(from)
//   //     disabledDays.after = new Date(to)
//   //   }
//   // }
//   return (
//     <div ref={inputRef} style={{ position: 'relative' }}>
//       <input
//         type='text'
//         value={
//           mode === 'single'
//             ? formatDate(selectedDate)
//             : formatRange(selectedRange)
//         }
//         onFocus={handleInputFocus}
//         readOnly
//         placeholder={
//           mode === 'single' ? 'Select a date' : 'Select a date range'
//         }
//         className={`my-2 flex h-9 rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring disabled:cursor-not-allowed disabled:opacity-50 ${mode === 'single' ? 'w-3/4' : 'w-1/2'}`}
//         disabled={disabled}
//         required={true}
//       />
//       {isPickerOpen && (
//         <div style={{ position: 'absolute', zIndex: 1000 }}>
//           <DayPicker
//             mode={mode}
//             selected={
//               mode === 'single'
//                 ? selectedDate
//                   ? new Date(selectedDate)
//                   : undefined
//                 : selectedRange && {
//                     from: selectedRange.from
//                       ? new Date(selectedRange.from)
//                       : undefined,
//                     to: selectedRange.to
//                       ? new Date(selectedRange.to)
//                       : undefined,
//                   }
//             }
//             // Pass the custom type for onSelect prop
//             onSelect={handleSelect}
//             classNames={defaultStyles}
//             // Disable dates before today
//             disabled={disabledDays}
//           />
//         </div>
//       )}
//     </div>
//   )
// }

// export default DateInput2
