jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FwYlwpService } from '../service/fw-ylwp.service';
import { IFwYlwp, FwYlwp } from '../fw-ylwp.model';

import { FwYlwpUpdateComponent } from './fw-ylwp-update.component';

describe('Component Tests', () => {
  describe('FwYlwp Management Update Component', () => {
    let comp: FwYlwpUpdateComponent;
    let fixture: ComponentFixture<FwYlwpUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let fwYlwpService: FwYlwpService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FwYlwpUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(FwYlwpUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FwYlwpUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      fwYlwpService = TestBed.inject(FwYlwpService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const fwYlwp: IFwYlwp = { id: 456 };

        activatedRoute.data = of({ fwYlwp });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(fwYlwp));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const fwYlwp = { id: 123 };
        spyOn(fwYlwpService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ fwYlwp });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: fwYlwp }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(fwYlwpService.update).toHaveBeenCalledWith(fwYlwp);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const fwYlwp = new FwYlwp();
        spyOn(fwYlwpService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ fwYlwp });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: fwYlwp }));
        saveSubject.complete();

        // THEN
        expect(fwYlwpService.create).toHaveBeenCalledWith(fwYlwp);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const fwYlwp = { id: 123 };
        spyOn(fwYlwpService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ fwYlwp });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(fwYlwpService.update).toHaveBeenCalledWith(fwYlwp);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
