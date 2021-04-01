import * as dayjs from 'dayjs';

export interface ICtClass {
  id?: number;
  dt?: dayjs.Dayjs;
  empn?: string;
  flag?: number | null;
  jbempn?: string | null;
  gotime?: dayjs.Dayjs | null;
  xj?: number | null;
  zp?: number | null;
  sk?: number | null;
  hyk?: number | null;
  cq?: number | null;
  gz?: number | null;
  gfz?: number | null;
  yq?: number | null;
  yh?: number | null;
  zzh?: number | null;
  checkSign?: string | null;
}

export class CtClass implements ICtClass {
  constructor(
    public id?: number,
    public dt?: dayjs.Dayjs,
    public empn?: string,
    public flag?: number | null,
    public jbempn?: string | null,
    public gotime?: dayjs.Dayjs | null,
    public xj?: number | null,
    public zp?: number | null,
    public sk?: number | null,
    public hyk?: number | null,
    public cq?: number | null,
    public gz?: number | null,
    public gfz?: number | null,
    public yq?: number | null,
    public yh?: number | null,
    public zzh?: number | null,
    public checkSign?: string | null
  ) {}
}

export function getCtClassIdentifier(ctClass: ICtClass): number | undefined {
  return ctClass.id;
}
