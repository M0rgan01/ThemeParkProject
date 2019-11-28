export class Toast {
  content: string;
  className: string;
  delay: number;


  constructor(content: string, className: string, delay: number) {
    this.content = content;
    this.className = className;
    this.delay = delay;
  }
}
