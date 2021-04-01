jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FwWxfService } from '../service/fw-wxf.service';
import { IFwWxf, FwWxf } from '../fw-wxf.model';

import { FwWxfUpdateComponent } from './fw-wxf-update.component';

describe('Component Tests', () => {
  describe('FwWxf Management Update Component', () => {
    let comp: FwWxfUpdateComponent;
    let fixture: ComponentFixture<FwWxfUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let fwWxfService: FwWxfService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FwWxfUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(FwWxfUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FwWxfUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      fwWxfService = TestBed.inject(FwWxfService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const fwWxf: IFwWxf = { id: 456 };

        activatedRoute.data = of({ fwWxf });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(fwWxf));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const fwWxf = { id: 123 };
        spyOn(fwWxfService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ fwWxf });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: fwWxf }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(fwWxfService.update).toHaveBeenCalledWith(fwWxf);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const fwWxf = new FwWxf();
        spyOn(fwWxfService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ fwWxf });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: fwWxf }));
        saveSubject.complete();

        // THEN
        expect(fwWxfService.create).toHaveBeenCalledWith(fwWxf);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const fwWxf = { id: 123 };
        spyOn(fwWxfService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ fwWxf });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(fwWxfService.update).toHaveBeenCalledWith(fwWxf);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
