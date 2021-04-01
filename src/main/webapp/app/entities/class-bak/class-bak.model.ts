import * as dayjs from 'dayjs';

export interface IClassBak {
  id?: number;
  empn?: string | null;
  dt?: dayjs.Dayjs | null;
  rq?: dayjs.Dayjs | null;
  ghname?: string | null;
  bak?: string | null;
}

export class ClassBak implements IClassBak {
  constructor(
    public id?: number,
    public empn?: string | null,
    public dt?: dayjs.Dayjs | null,
    public rq?: dayjs.Dayjs | null,
    public ghname?: string | null,
    public bak?: string | null
  ) {}
}

export function getClassBakIdentifier(classBak: IClassBak): number | undefined {
  return classBak.id;
}
