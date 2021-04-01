import * as dayjs from 'dayjs';

export interface IDRk {
  id?: number;
  rkdate?: dayjs.Dayjs;
  depot?: string;
  rklx?: string | null;
  rkbillno?: string;
  company?: number | null;
  deptname?: string | null;
  jbr?: string | null;
  remark?: string | null;
  empn?: string | null;
  lrdate?: dayjs.Dayjs | null;
  spbm?: string;
  spmc?: string;
  ggxh?: string | null;
  dw?: string | null;
  price?: number | null;
  sl?: number | null;
  je?: number | null;
  sign?: number | null;
  memo?: string | null;
  flag?: number | null;
  f1?: string | null;
  f2?: string | null;
  f1empn?: string | null;
  f2empn?: string | null;
  f1sj?: dayjs.Dayjs | null;
  f2sj?: dayjs.Dayjs | null;
}

export class DRk implements IDRk {
  constructor(
    public id?: number,
    public rkdate?: dayjs.Dayjs,
    public depot?: string,
    public rklx?: string | null,
    public rkbillno?: string,
    public company?: number | null,
    public deptname?: string | null,
    public jbr?: string | null,
    public remark?: string | null,
    public empn?: string | null,
    public lrdate?: dayjs.Dayjs | null,
    public spbm?: string,
    public spmc?: string,
    public ggxh?: string | null,
    public dw?: string | null,
    public price?: number | null,
    public sl?: number | null,
    public je?: number | null,
    public sign?: number | null,
    public memo?: string | null,
    public flag?: number | null,
    public f1?: string | null,
    public f2?: string | null,
    public f1empn?: string | null,
    public f2empn?: string | null,
    public f1sj?: dayjs.Dayjs | null,
    public f2sj?: dayjs.Dayjs | null
  ) {}
}

export function getDRkIdentifier(dRk: IDRk): number | undefined {
  return dRk.id;
}
