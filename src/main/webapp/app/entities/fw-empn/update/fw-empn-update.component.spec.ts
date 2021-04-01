jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FwEmpnService } from '../service/fw-empn.service';
import { IFwEmpn, FwEmpn } from '../fw-empn.model';

import { FwEmpnUpdateComponent } from './fw-empn-update.component';

describe('Component Tests', () => {
  describe('FwEmpn Management Update Component', () => {
    let comp: FwEmpnUpdateComponent;
    let fixture: ComponentFixture<FwEmpnUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let fwEmpnService: FwEmpnService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FwEmpnUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(FwEmpnUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FwEmpnUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      fwEmpnService = TestBed.inject(FwEmpnService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const fwEmpn: IFwEmpn = { id: 456 };

        activatedRoute.data = of({ fwEmpn });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(fwEmpn));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const fwEmpn = { id: 123 };
        spyOn(fwEmpnService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ fwEmpn });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: fwEmpn }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(fwEmpnService.update).toHaveBeenCalledWith(fwEmpn);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const fwEmpn = new FwEmpn();
        spyOn(fwEmpnService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ fwEmpn });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: fwEmpn }));
        saveSubject.complete();

        // THEN
        expect(fwEmpnService.create).toHaveBeenCalledWith(fwEmpn);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const fwEmpn = { id: 123 };
        spyOn(fwEmpnService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ fwEmpn });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(fwEmpnService.update).toHaveBeenCalledWith(fwEmpn);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
